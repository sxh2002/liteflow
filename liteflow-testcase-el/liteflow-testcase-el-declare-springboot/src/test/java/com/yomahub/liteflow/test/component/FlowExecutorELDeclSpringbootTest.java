package com.yomahub.liteflow.test.component;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;

/**
 * 组件功能点测试 单元测试
 *
 * @author donguo.tao
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(value = "classpath:/component/application.properties")
@SpringBootTest(classes = FlowExecutorELDeclSpringbootTest.class)
@EnableAutoConfiguration
@ComponentScan({ "com.yomahub.liteflow.test.component.cmp1", "com.yomahub.liteflow.test.component.cmp2" })
public class FlowExecutorELDeclSpringbootTest extends BaseTest {

	private static final Logger LOG = LoggerFactory.getLogger(FlowExecutorELDeclSpringbootTest.class);

	@Resource
	private FlowExecutor flowExecutor;

	// isAccess方法的功能测试
	@Test
	public void testIsAccess() {
		LiteflowResponse response = flowExecutor.execute2Resp("chain1", 101);
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getSlot().getResponseData());
	}

	// 组件抛错的功能点测试
	public void testComponentException() throws Exception {
		Assertions.assertThrows(ArithmeticException.class, () -> {
			LiteflowResponse response = flowExecutor.execute2Resp("chain2", 0);
			Assertions.assertFalse(response.isSuccess());
			Assertions.assertEquals("/ by zero", response.getMessage());
			ReflectionUtils.rethrowException(response.getCause());
		});
	}

	// isContinueOnError方法的功能点测试
	@Test
	public void testIsContinueOnError() throws Exception {
		LiteflowResponse response = flowExecutor.execute2Resp("chain3", 0);
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNull(response.getCause());
	}

	// isEnd方法的功能点测试
	@Test
	public void testIsEnd() throws Exception {
		LiteflowResponse response = flowExecutor.execute2Resp("chain4", 10);
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertEquals("d", response.getExecuteStepStr());
	}

	// setIsEnd方法的功能点测试
	@Test
	public void testSetIsEnd1() throws Exception {
		LiteflowResponse response = flowExecutor.execute2Resp("chain5", 10);
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertEquals("e", response.getExecuteStepStr());
	}

	// 条件组件的功能点测试
	@Test
	public void testNodeCondComponent() {
		LiteflowResponse response = flowExecutor.execute2Resp("chain6", 0);
		Assertions.assertTrue(response.isSuccess());
	}

	// 测试setIsEnd如果为true，continueError也为true，那不应该continue了
	@Test
	public void testSetIsEnd2() throws Exception {
		LiteflowResponse response = flowExecutor.execute2Resp("chain7", 10);
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertEquals("g", response.getExecuteStepStr());
	}

}
