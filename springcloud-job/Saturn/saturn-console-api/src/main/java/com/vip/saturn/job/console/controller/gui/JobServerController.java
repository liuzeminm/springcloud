package com.vip.saturn.job.console.controller.gui;

import com.vip.saturn.job.console.controller.SuccessResponseEntity;
import com.vip.saturn.job.console.domain.RequestResult;
import com.vip.saturn.job.console.exception.SaturnJobConsoleException;
import com.vip.saturn.job.console.service.JobService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Job servers' status for specified job.
 *
 * @author kfchu
 */
@RequestMapping("/console/namespaces/{namespace:.+}/jobs/{jobName}/servers")
public class JobServerController extends AbstractGUIController {
	@Resource
	private JobService jobService;

	/**
	 * 获取作业执行状态
	 */
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success/Fail", response = RequestResult.class)})
	@GetMapping(value = "/status")
	public SuccessResponseEntity getJobServersStatus(final HttpServletRequest request, @PathVariable String namespace,
			@PathVariable String jobName) throws SaturnJobConsoleException {
		return new SuccessResponseEntity(jobService.getJobServersStatus(namespace, jobName));
	}
}
