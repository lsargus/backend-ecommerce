package br.com.ecommerce.controllers;

import br.com.ecommerce.domains.CartStatus;
import br.com.ecommerce.domains.CartUpdateMessage;
import br.com.ecommerce.services.BookService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final BookService bookService;

    public WebSocketController(BookService bookService) {
        this.bookService = bookService;
    }

    @MessageMapping("/updateCart")
    @SendTo("/topic/cartUpdate")
    public CartStatus updateCart(CartUpdateMessage message) throws Exception {
        // Lógica para atualizar o carrinho e verificar o estoque
        // Por exemplo, reduza o estoque e atualize o carrinho
        CartStatus status = checkAndUpdateStock(message.getProductId(), message.getQuantity());

        // Retorna o status do carrinho, que será enviado para o tópico "/topic/cartUpdate"
        return status;
    }

    // Simulação de método para atualizar estoque e carrinho
    private CartStatus checkAndUpdateStock(Long productId, int quantity) {
        var stock = bookService.checkStock(productId);

        String msg = "";
        String status = "available";
        if (stock == 0) {
            msg = "Not enough stock";
            status = "unavailable";
        } else if (stock < quantity) {
            msg = "Estoque insuficiente, só possuimos " + stock + " unidades em estoque.";
            status = "partially unavailable";
        } else if (stock <= 5) {
            msg = "Existem poucas unidades em estoque";
            status = "low stock";
        }
        // Implemente a lógica de verificação de estoque
        return new CartStatus(productId, status, msg);
    }
}
