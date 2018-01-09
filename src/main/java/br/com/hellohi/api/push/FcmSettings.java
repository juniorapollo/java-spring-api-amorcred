/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.push;

import de.bytefish.fcmjava.http.options.IFcmClientSettings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author junior
 */

@ConfigurationProperties(prefix = "fcm")
@Component
public class FcmSettings implements IFcmClientSettings{
    private String apiKey;
  private String url;
  
   @Override
  public String getApiKey() {
    return this.apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String getFcmUrl() {
    return this.url;
  }

    
  
}
