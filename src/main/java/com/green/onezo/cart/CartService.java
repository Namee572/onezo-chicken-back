package com.green.onezo.cart;

import com.green.onezo.member.Member;
import com.green.onezo.member.MemberRepository;
import com.green.onezo.menu.Menu;
import com.green.onezo.menu.MenuRepository;
import com.green.onezo.store.Store;
import com.green.onezo.store.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final CartItemRepository cartItemRepository;

    // 장바구니 아이템 추가
    public CartItem addCartItem(CartItemDto.CartItemReq cartItemReq) {

        Member member = memberRepository.findById(cartItemReq.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Store store = storeRepository.findById(cartItemReq.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));
        Menu menu = menuRepository.findById(cartItemReq.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        if (menu.getStock().equals("Out of stock")) {
            throw new RuntimeException("재고가 부족합니다.");
        }

        CartItem cartItem = CartItem.builder()
                .member(member)
                .store(store)
                .menu(menu)
                .quantity(cartItemReq.getQuantity())
                .build();
        return cartItemRepository.save(cartItem);
    }

    // 장바구니 조회
    public List<CartItemDto.CartItemRes> getCart(Long memberId) {
        return cartItemRepository.findByMemberId(memberId).stream()
                .map(item -> CartItemDto.CartItemRes.builder()
                        .cartItemId(item.getId())
                        .storeName(item.getStore().getStoreName())
                        .menuName(item.getMenu().getMenuName())
                        .quantity(item.getQuantity())
                        .price(item.getMenu().getPrice())
                        .build())
                .collect(Collectors.toList());
    };
    // 장바구니 아이템 업데이트
    @Transactional
    public void updateCartItem(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    // 장바구니 아이템 삭제
    @Transactional
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}