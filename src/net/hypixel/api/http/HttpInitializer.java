package net.hypixel.api.http;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import net.hypixel.api.util.Callback;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelInitializer;
import net.minecraft.util.io.netty.handler.codec.http.HttpClientCodec;
import net.minecraft.util.io.netty.handler.ssl.SslHandler;
import net.minecraft.util.io.netty.handler.timeout.ReadTimeoutHandler;

public class HttpInitializer<T> extends ChannelInitializer<Channel> {

    private final boolean ssl;
    private final Callback<T> callback;
    private final Gson gson;

    public HttpInitializer(boolean ssl, Callback<T> callback, Gson gson) {
        this.ssl = ssl;
        this.callback = callback;
        this.gson = gson;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast("timeout", new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
        if (ssl) {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);
            SSLEngine engine = context.createSSLEngine();
            engine.setUseClientMode(true);
            ch.pipeline().addLast("ssl", new SslHandler(engine));
        }
        ch.pipeline().addLast("http", new HttpClientCodec());
        ch.pipeline().addLast("handler", new HttpHandler<>(callback, gson));
    }
}
