package com.qfw.platform.cache;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

public class CustomGenericManageableCaptchaService extends
		DefaultManageableImageCaptchaService {

	@Override
	public Boolean validateResponseForID(String ID, Object response)
			throws CaptchaServiceException {
		if (!this.store.hasCaptcha(ID)) {
			throw new CaptchaServiceException(
					"Invalid ID, could not validate unexisting or already validated captcha");
		}
		Boolean valid = this.store.getCaptcha(ID).validateResponse(response);
		return valid;
	}

	public void removeCaptcha(String sessionId) {
		if (sessionId != null && this.store.hasCaptcha(sessionId)) {
			this.store.removeCaptcha(sessionId);
		}
	}

}