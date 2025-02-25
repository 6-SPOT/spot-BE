package spot.spot.domain.chat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import spot.spot.domain.chat.dto.request.ChatRoomCreateRequest;
import spot.spot.domain.chat.dto.response.ChatListResponse;
import spot.spot.domain.chat.dto.response.ChatMessageResponse;
import spot.spot.domain.chat.service.ChatService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

	private final ChatService chatService;

	// 내 채팅방 목록 조회
	@GetMapping("/my/rooms")
	public ResponseEntity<?> getMyChatRooms(Authentication authentication) {
		Long memberId = Long.parseLong(authentication.getName());
		List<ChatListResponse> chatListResponses = chatService.getMyChatRooms(memberId);
		return new ResponseEntity<>(chatListResponses, HttpStatus.OK);
	}

	@GetMapping("/my/rooms/test")
	public ResponseEntity<?> getMyChatRoomsTest() {
		ChatListResponse chatListResponse1 = ChatListResponse.builder()
			.title("채팅방제목입니다")
			.roomId(1L)
			.build();
		ChatListResponse chatListResponse2 = ChatListResponse.builder()
			.title("채팅방제목2입니다")
			.roomId(2L)
			.build();
		List<ChatListResponse> chatListResponses = List.of(chatListResponse1,chatListResponse2);
		// List<ChatListResponse> chatListResponses = chatService.getMyChatRooms(memberId);
		return new ResponseEntity<>(chatListResponses, HttpStatus.OK);
	}

	// 채팅 신청
	@PostMapping("/room/create")
	public ResponseEntity<?> getOrCreateChatRoom(@RequestBody ChatRoomCreateRequest chatRoomCreateRequest,
		Authentication authentication) {
		Long memberId = Long.parseLong(authentication.getName());
		Long roomId = chatService.getOrCreateChatRoom(chatRoomCreateRequest, memberId);
		return new ResponseEntity<>(roomId, HttpStatus.OK);
	}

	// 이전 메시지 조회
	@GetMapping("/history/{roomId}")
	public ResponseEntity<?> getChatHistory(@PathVariable Long roomId, Authentication authentication) {
		Long memberId = Long.parseLong(authentication.getName());
		List<ChatMessageResponse> chatMessageResponses = chatService.getChatHistory(roomId, memberId);
		return new ResponseEntity<>(chatMessageResponses, HttpStatus.OK);
	}

	// 이전 메시지 조회 테스트
	@GetMapping("/history/{roomId}/test")
	public ResponseEntity<?> getChatHistoryTest(@PathVariable Long roomId) {
		ChatMessageResponse chatMessageResponse = ChatMessageResponse.builder()
			.sender("보낸이")
			.content("되었으면 좋겠습니다.")
			.build();
		List<ChatMessageResponse> chatMessageResponses = List.of(chatMessageResponse);
		// List<ChatMessageResponse> chatMessageResponses = chatService.getChatHistory(roomId, memberId);
		return new ResponseEntity<>(chatMessageResponses, HttpStatus.OK);
	}

	// 채팅 메세지 읽음 처리
	@PostMapping("/room/{roomId}/read")
	public ResponseEntity<?> messageRead(@PathVariable Long roomId, Authentication authentication) {
		Long memberId = Long.parseLong(authentication.getName());
		chatService.messageRead(roomId, memberId);
		return ResponseEntity.ok().build();
	}

}
