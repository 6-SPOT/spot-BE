package spot.spot.domain.chat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/chat")
public class ChatController {

	private final ChatService chatService;

	// 내 채팅방 목록 조회
	@GetMapping("/my/rooms")
	public ResponseEntity<?> getMyChatRooms() {
		List<ChatListResponse> chatListResponses = chatService.getMyChatRooms();
		return new ResponseEntity<>(chatListResponses, HttpStatus.OK);
	}

	// 채팅 신청
	@PostMapping("/room/create")
	public ResponseEntity<?> getOrCreateChatRoom(@RequestBody ChatRoomCreateRequest chatRoomCreateRequest) {
		Long roomId = chatService.getOrCreateChatRoom(chatRoomCreateRequest);
		return new ResponseEntity<>(roomId, HttpStatus.OK);
	}

	// 이전 메시지 조회
	@GetMapping("/history/{roomId}")
	public ResponseEntity<?> getChatHistory(@PathVariable Long roomId) {
		List<ChatMessageResponse> chatMessageResponses = chatService.getChatHistory(roomId);
		return new ResponseEntity<>(chatMessageResponses, HttpStatus.OK);
	}

	// 채팅 메세지 읽음 처리
	@PostMapping("/room/{roomId}/read")
	public ResponseEntity<?> messageRead(@PathVariable Long roomId) {
		chatService.messageRead(roomId);
		return ResponseEntity.ok().build();
	}

}
