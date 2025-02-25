package spot.spot.domain.job.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.*;
import spot.spot.domain.job.dto.request.RegisterJobRequest;
import spot.spot.domain.job.dto.response.AttenderResponse;
import spot.spot.domain.job.entity.Job;
import spot.spot.domain.member.entity.Worker;
import spot.spot.domain.member.entity.WorkerAbility;
import spot.spot.domain.pay.entity.PayHistory;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface Job4ClientMapper {
    @Mapping(source = "tid", target = "tid")
    @Mapping(source = "payHistory", target = "payment")
    Job registerRequestToJob (String img, RegisterJobRequest request, String tid, PayHistory payHistory);

    @Mapping(source = "member.id", target = "id")
    @Mapping(source = "member.nickname", target = "name")
    @Mapping(source = "member.img", target = "profile_img")
    @Mapping(source = "member.lat", target = "lat")
    @Mapping(source = "member.lng", target = "lng")
    @Mapping(source = "workerAbilities", target = "abilities", qualifiedByName = "mapAbilities")
    AttenderResponse toResponse(Worker worker);

    @IterableMapping(elementTargetType = AttenderResponse.class)
    List<AttenderResponse> toResponseList(List<Worker> workers);

    @Named("mapAbilities")
    default List<String> mapAbilities(List<WorkerAbility> workerAbilities) {
        return workerAbilities != null
            ? workerAbilities.stream()
            .map(workerAbility -> workerAbility.getAbility().getType().name()) // ✅ Ability의 name 값만 변환
            .distinct() // ✅ 중복 제거
            .collect(Collectors.toList())
            : Collections.emptyList();
    }



}
