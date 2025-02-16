package spot.spot.domain.notification.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spot.spot.domain.member.entity.Member;
import spot.spot.domain.member.repository.MemberRepository;
import spot.spot.domain.notification.dto.request.UpdateFcmTokenRequest;
import spot.spot.domain.notification.entity.FcmToken;
import spot.spot.domain.notification.repository.FcmTokenRepository;
import spot.spot.global.response.format.ErrorCode;
import spot.spot.global.response.format.GlobalException;
import spot.spot.global.security.util.UserAccessUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmService {
    private final FcmTokenRepository fcmTokenRepository;
    private final UserAccessUtil userAccessUtil;

    public void saveFcmToken(UpdateFcmTokenRequest request) {
        Member member = userAccessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        String value = request.fcmToken();
        fcmTokenRepository.save(FcmToken.builder()
            .member(member)
            .data(value)
            .build());
    }
}
