package com.customautosys.cross_origin_isolated_headers;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CrossOriginIsolatedHeaders extends CordovaPlugin{
	protected SystemWebViewEngine systemWebViewEngine;
	protected SystemWebView systemWebView;

	@Override
	public void pluginInitialize(){
		System.out.println("pluginInitialize");
		this.systemWebViewEngine=(SystemWebViewEngine)this.webView.getEngine();
		this.systemWebView=(SystemWebView)this.webView.getView();
		Log.d("initialize - cordovaInterface",this.cordova.toString());
		Log.d("initialize - cordovaWebView",this.webView.toString());
		Log.d("initialize - systemWebViewEngine",this.systemWebViewEngine.toString());
		Log.d("initialize - systemWebView",this.systemWebView.toString());

    	this.systemWebView.setWebViewClient(new SystemWebViewClient(this.systemWebViewEngine){
			@Override
			public WebResourceResponse shouldInterceptRequest(
				WebView webView,
				WebResourceRequest request
			){
				Log.d("shouldInterceptRequest - request", request.getUrl().toString());
				WebResourceResponse response=super.shouldInterceptRequest(
					webView,
					request
				);

				if(request.getUrl().toString().contains("file:///android_asset/www/index.html")&&response!=null){
					Log.d("shouldInterceptRequest - response", response.toString());
					Map<String,String> headers=response.getResponseHeaders();
					if(headers==null){
						headers=new HashMap<>();
					}

					//Set these 3 headers because these should be what are required to use SharedArrayBuffer

					headers.put(
						"Cross-Origin-Opener-Policy",
						"same-origin"
					);
					headers.put(
						"Cross-Origin-Embedder-Policy",
						"credentialless"
					);
					headers.put(
						"Cross-Origin-Resource-Policy",
						"cross-origin"
					);

					response.setResponseHeaders(headers);
				}

				return response;
			}
		});
	}
}