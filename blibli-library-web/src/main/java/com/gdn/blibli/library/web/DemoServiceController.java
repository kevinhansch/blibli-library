package com.gdn.blibli.library.web;


import com.blibli.oss.backend.common.helper.ResponseHelper;
import com.blibli.oss.backend.common.model.response.Response;
import com.blibli.oss.backend.mandatoryparameter.swagger.annotation.MandatoryParameterAtHeader;
import com.gdn.blibli.library.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "api/service",
    produces = MediaType.APPLICATION_JSON_VALUE)
@MandatoryParameterAtHeader
public class DemoServiceController {

  @Autowired
  private DemoService demoService;

  @GetMapping(value = "/demo")
  public Response<Boolean> demoService() {
    log.info("start #demoService for requestId: ");
    try {
      this.demoService.demo1();
      this.demoService.demo2();

      return ResponseHelper.ok();
    } catch (Exception ex) {
      log.error("#demoService error, caused by: {}", ex.getMessage(), ex);
      return ResponseHelper.internalServerError();
    }
  }
}
