package com.lace.service;

import com.lace.model.response.GenericResponse;
import java.util.List;

/**
 *
 * @author hackdaemon
 */
public interface AuthenticationService {
  
  GenericResponse doUserLogin();
  GenericResponse doUserSignup(List<Object> dataList);
}
