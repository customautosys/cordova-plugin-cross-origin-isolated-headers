package com.customautosys.cross_origin_isolated_headers;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import org.apache.cordova.CordovaPlugin;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CrossOriginIsolatedHeaders extends CordovaPlugin{

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
}