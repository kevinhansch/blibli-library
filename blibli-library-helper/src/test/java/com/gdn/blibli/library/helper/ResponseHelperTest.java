package com.gdn.blibli.library.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.gdn.common.web.base.BaseResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.referral.service.client.model.game.GameAllocationResponse;
import com.gdn.referral.service.client.model.game.GamificationApiResponse;
import com.gdn.referral.service.entity.Child;
import com.gdn.referral.service.entity.Configuration;
import com.gdn.referral.service.entity.ConfigurationV2;
import com.gdn.referral.service.entity.ConfigurationV2.RewardConfiguration;
import com.gdn.referral.service.entity.Parent;
import com.gdn.referral.service.entity.ParentReward;
import com.gdn.referral.service.entity.Program;
import com.gdn.referral.service.enums.CompletionType;
import com.gdn.referral.service.enums.JoinType;
import com.gdn.referral.service.enums.RewardType;
import com.gdn.referral.service.enums.Status;
import com.gdn.referral.service.web.model.child.ChildJoinWebResponse;
import com.gdn.referral.service.web.model.parent.GetParentWebResponse;
import com.gdn.referral.service.web.model.program.ProgramWebResponse;
import com.gdn.x.promotion.rest.web.model.dto.response.AssignCouponResponse;


public class ResponseHelperTest {

  private static final String COUPON_ID = "couponID";

  @InjectMocks
  private ResponseHelper responseHelper;

  @Test
  public void toConfigurationV2WebModelTest() {
    ConfigurationV2 config = new ConfigurationV2();
    RewardConfiguration reward = new RewardConfiguration();
    reward.setRewardType(RewardType.GAME_BACKEND);
    reward.setRewardValue("a;b");
    config.setMax(100);
    config.setRewards(Collections.singletonMap(0, reward));

    assertNotNull(ResponseHelper.toConfigurationV2WebModel(config));

    ConfigurationV2 config2 = new ConfigurationV2();
    config2.setMax(100);
    config2.setRewards(Collections.singletonMap(0, new RewardConfiguration()));
    assertNotNull(ResponseHelper.toConfigurationV2WebModel(config2));
  }

  @Test
  public void toParentWebResponseTest() {
    assertNotNull(ResponseHelper.toParentWebResponse(new Parent()));
  }

  @Test
  public void setBaseResponseTest() {
    ResponseHelper.setBaseResponse(new GetParentWebResponse(), new Parent());
  }

  @Test
  public void toConfigurationWebModelTest() {
    assertNotNull(
        ResponseHelper.toConfigurationWebModel(new Configuration(RewardType.BLIBLI, "value", 10)));
  }

  @Test
  public void toConfigurationWebModelNullRewardTypeTest() {
    assertNotNull(
        ResponseHelper.toConfigurationWebModel(new Configuration(null, "value", 10)));
  }

  @Test
  public void toProgramWebResponseTest_Exception() {
    try {
      ResponseHelper.toProgramWebResponse(null, ProgramWebResponse.class);
    } catch (Exception e) {

    }
  }

  @Test
  public void toProgramWebResponseTest_Exception2() {
    try {
      Program program = new Program();
      Configuration config = new Configuration(RewardType.BLIBLI, "value", 1);
      ConfigurationV2 configV2 = new ConfigurationV2(Collections.emptyMap(), 100);
      program.setJoinType(JoinType.MANUAL);
      program.setCompletionType(CompletionType.PURCHASE);
      program.setParent(configV2);
      program.setChild(config);
      ResponseHelper.toProgramWebResponse(program, ProgramWebResponse.class);
    } catch (Exception e) {

    }
  }

  @Test
  public void toProgramWebResponseTest() {
    Program program = new Program();
    Configuration config = new Configuration(RewardType.BLIBLI, "value", 1);
    ConfigurationV2 configV2 = new ConfigurationV2(Collections.emptyMap(), 100);
    program.setJoinType(JoinType.MANUAL);
    program.setCompletionType(CompletionType.PURCHASE);
    program.setParent(configV2);
    program.setChild(config);
    ResponseHelper.toProgramWebResponse(program, ProgramWebResponse.class);
  }

  @Test
  public void toProgramWebResponseNullExceptionTest() {
    class Testclz extends ProgramWebResponse
    {}
    Program program = new Program();
    Testclz result = ResponseHelper.toProgramWebResponse(program, Testclz.class);
    assertNull(result);
  }

  @BeforeEach
  public void setUp() throws Exception {
    initMocks(this);
  }

  @Test
  public void setBaseResponse() {
    Child child = new Child();
    child.setId("ID");
    child.setVersion(1L);
    child.setCreatedBy("createdBy");
    child.setCreatedDate(123L);
    child.setStoreId("10001");
    child.setUpdatedBy("updatedBy");
    child.setUpdatedDate(123L);
    ChildJoinWebResponse response = new ChildJoinWebResponse();
    ChildJoinWebResponse result = ResponseHelper.setBaseResponse(response, child);
    assertEquals(result.getId(), child.getId());
    assertEquals(result.getVersion(), child.getVersion());
    assertEquals(result.getCreatedBy(), child.getCreatedBy());
    assertEquals(result.getCreatedDate(), child.getCreatedDate());
    assertEquals(result.getStoreId(), child.getStoreId());
    assertEquals(result.getUpdatedBy(), child.getUpdatedBy());
    assertEquals(result.getUpdatedDate(), child.getUpdatedDate());
  }

  @Test
  public void isResponseValid() {
    GdnRestSingleResponse nullValue = new GdnRestSingleResponse(null, null);
    GdnRestSingleResponse nullRestSingleResponse = null;
    GdnRestSingleResponse[] lengthRestSingleZero = new GdnRestSingleResponse[] {};
    BaseResponse baseResponse = new BaseResponse();
    GdnRestSingleResponse invalidResponse =
        new GdnRestSingleResponse("", "", false, baseResponse, "requestId");
    GdnRestSingleResponse validResponse =
        new GdnRestSingleResponse("", "", true, baseResponse, "requestId");
    assertFalse(ResponseHelper.isResponseValid(nullValue));
    assertFalse(ResponseHelper.isResponseValid(nullRestSingleResponse));
    assertFalse(ResponseHelper.isResponseValid(lengthRestSingleZero));
    assertFalse(ResponseHelper.isResponseValid(invalidResponse));
    assertTrue(ResponseHelper.isResponseValid(validResponse));
  }

  @Test
  public void setCouponIdToParentTest() {
    AssignCouponResponse assignCouponResponse = new AssignCouponResponse();
    assignCouponResponse.setCouponId(COUPON_ID);
    Child child = new Child();
    ParentReward parentReward = new ParentReward();
    child.setParentReward(parentReward);
    Child result = ResponseHelper.setCouponIdToParent(assignCouponResponse, child);
    assertEquals(COUPON_ID, result.getParentReward().getId());
    assertEquals(Status.COMPLETE, result.getParentReward().getStatus());
  }

  @Test
  public void validateGameResponseTest() {
    assertTrue(ResponseHelper.validateGamificationResponse(
        new GamificationApiResponse<GameAllocationResponse>(null, null, null, "true")));
  }
}
