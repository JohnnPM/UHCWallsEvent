package net.hypixel.api.http;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

import net.hypixel.api.util.Callback;
import net.minecraft.util.com.google.common.net.MediaType;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.io.netty.bootstrap.Bootstrap;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.channel.ChannelFuture;
import net.minecraft.util.io.netty.channel.ChannelFutureListener;
import net.minecraft.util.io.netty.channel.ChannelOption;
import net.minecraft.util.io.netty.channel.nio.NioEventLoopGroup;
import net.minecraft.util.io.netty.channel.socket.nio.NioSocketChannel;
import net.minecraft.util.io.netty.handler.codec.http.DefaultFullHttpRequest;
import net.minecraft.util.io.netty.handler.codec.http.DefaultHttpRequest;
import net.minecraft.util.io.netty.handler.codec.http.FullHttpRequest;
import net.minecraft.util.io.netty.handler.codec.http.HttpHeaders;
import net.minecraft.util.io.netty.handler.codec.http.HttpMethod;
import net.minecraft.util.io.netty.handler.codec.http.HttpRequest;
import net.minecraft.util.io.netty.handler.codec.http.HttpVersion;

public class HTTPClient {
    private static final int CONNECT_TIMEOUT = 5000;
    private static final Gson gson = new Gson();
    private final Bootstrap bootstrap;
    private final NioEventLoopGroup eventLoop;

    public HTTPClient() {
        this(0);
    }

    public HTTPClient(int nThreads) {
        eventLoop = new NioEventLoopGroup(nThreads);
        bootstrap = new Bootstrap()
                .channel(NioSocketChannel.class)
                .group(eventLoop)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT);
    }

    public void close() {
        eventLoop.shutdownGracefully();
    }

    private String getPath(URI uri) {
        return uri.getRawPath() + ((uri.getRawQuery() == null) ? "" : "?" + uri.getRawQuery());
    }

    public <T> void post(String url, ByteBuf content, MediaType mediaType, final Callback<T> callback) {
        URI uri = URI.create(url);
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, url, content);
        request.headers().set("Content-Length", (long) content.readableBytes());
        request.headers().set("Content-Type", mediaType);
        request(uri, request, callback);
    }

    public <T> void get(String url, final Callback<T> callback) {
        URI uri = URI.create(url);
        request(uri, new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, getPath(uri)), callback);
    }

    public <T> void request(final URI uri, final HttpRequest request, final Callback<T> callback) {
        int port = uri.getPort();
        boolean ssl = uri.getScheme().equals("https");
        if (port == -1) {
            switch (uri.getScheme()) {
                case "http":
                    port = 80;
                    break;
                case "https":
                    port = 443;
                    break;
                default:
                    throw new RuntimeException("Unknown scheme " + uri.getScheme());
            }
        }
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(uri.getHost());
        } catch (UnknownHostException e) {
            callback.callback(e, null);
            return;
        }
        ChannelFutureListener future = new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    request.headers().set(HttpHeaders.Names.HOST, uri.getHost());
                    future.channel().writeAndFlush(request);
                } else {
                    callback.callback(future.cause(), null);
                }
            }
        };
        bootstrap.handler(new HttpInitializer<>(ssl, callback, gson)).connect(inetAddress, port).addListener(future);
    }
}
