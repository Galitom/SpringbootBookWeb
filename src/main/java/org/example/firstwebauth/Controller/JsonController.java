package org.example.firstwebauth.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.firstwebauth.Model.Book;
import org.example.firstwebauth.Model.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class JsonController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/sincronizza")
    public String sincronizza(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/books/v1/volumes?q=quilting";

        // Make the request to the Google Books API
        String response = restTemplate.getForObject(url, String.class);

        // Parse the JSON response using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode items = objectMapper.readTree(response).path("items");

            // Process each book in the list
            for (JsonNode item : items) {
                String title = item.path("volumeInfo").path("title").asText();
                String author = item.path("volumeInfo").path("authors").get(0).asText();
                String description = item.path("volumeInfo").path("description").asText();
                double listPrice = item.path("saleInfo").path("listPrice").path("amount").asDouble();


                if (description.length() > 30){
                    description = description.substring(0, 29) + "...";
                }


                Book book = new Book();
                book.setTitolo(title);
                book.setAutore(author);
                book.setDescrizione(description);
                book.setPrice(listPrice);

                //System.out.println(book);
                bookRepository.save(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/home";
    }
}
