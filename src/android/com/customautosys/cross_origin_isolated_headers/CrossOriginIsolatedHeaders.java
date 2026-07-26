package com.customautosys.cross_origin_isolated_headers;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CrossOriginIsolatedHeaders extends CordovaPlugin{
	protected CordovaInterface cordovaInterface;
	protected CordovaWebView cordovaWebView;

	@Override
	public void initialize(CordovaInterface cordovaInterface,CordovaWebView cordovaWebView){
		this.cordovaInterface=cordovaInterface;
		this.cordovaWebView=cordovaWebView;

    	this.cordovaWebView.setWebViewClient(new SystemWebViewClient((SystemWebViewEngine)this.cordovaWebView.getEngine()){
			@Override
			public WebResourceResponse shouldInterceptRequest(
				WebView webView,
				WebResourceRequest request
			){
				WebResourceResponse response=super.shouldInterceptRequest(
					webView,
					request
				);

				if(response!=null){
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