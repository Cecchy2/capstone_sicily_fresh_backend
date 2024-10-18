package dariocecchinato.capstone_sicily_fresh.tools;

import dariocecchinato.capstone_sicily_fresh.entities.Utente;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunSender {
    private String apiKey;
    private String domainName;

    public MailgunSender(@Value("${mailgun.key}") String apiKey,@Value("${mailgun.domain}") String domainName) {
        this.apiKey = apiKey;
        this.domainName = domainName;
    }

    public void sendRegistrationEmail(Utente recipient){
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
  			.basicAuth("api", this.apiKey)
                .queryString("from", "dariocecchinato@gmail.com")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registrazione Completata")
                .queryString("text", "Ciao " + recipient.getNome() + ", grazie per esserti registrato")
                .asJson();
        System.out.println(request.getBody());

    }

    public void sendOrdineRicetteEmail(Utente recipient){
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "dariocecchinato@gmail.com")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Ordine Effettuato")
                .queryString("text", "Ciao " + recipient.getNome() + ", ti confermo che hai effettuato l' ordine")
                .asJson();
        System.out.println(request.getBody());
    }
}
