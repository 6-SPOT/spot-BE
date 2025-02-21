package spot.spot.domain.pay.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import spot.spot.domain.pay.entity.dto.PointServeRequestDto;
import spot.spot.domain.pay.service.PointService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point")
public class PointController {

    private final PointService pointService;

    @PostMapping("/serve")
    public void servePointCoupon(@RequestBody PointServeRequestDto requestDto) {
        requestDto.registerDto().stream().forEach(
                pointRegisterDto -> pointService.servePoint(
                        pointRegisterDto.pointName(),
                        pointRegisterDto.point(),
                        pointRegisterDto.pointCode())
        );
    }

    @GetMapping("/register")
    public void registerPointCoupon(@RequestParam String pointCode, Authentication auth) {
        pointService.registerPoint(pointCode, auth.getName());
    }

    @PostMapping("/delete")
    public void deletePointCoupon(@RequestParam String pointCode) {
        pointService.deletePoint(pointCode);
    }
}
