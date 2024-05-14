package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OtodomParser {

    public static final String MAIN_PAGE_URL = "https://www.otodom.pl";
    private Document page;

    public OtodomParser(String url) {
        try {
            page = Jsoup.connect(url).get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Set<String> getOffersUrls(){
        Elements ulElements = page.select("ul");

        return  ulElements.stream()
                .filter(e -> e.className().equals("css-rqwdxd e1tno8ef0"))
                .flatMap(e -> e.select("a")
                        .stream()
                        .map(el -> el.attribute("href").getValue())) // Use flatMap to flatten nested streams
                .collect(Collectors.toSet());
    }

    public List<String> getOfferDescriptionFromUrl(Set<String> offersUrls) throws Exception{
        List<String> offersWithDescription = new ArrayList<>();
        for (String href :
                offersUrls) {
            Document offerPage = Jsoup.connect(MAIN_PAGE_URL + href)
                    .get();

            StringBuilder offerText = new StringBuilder();

            getOfferInfoFromHeader(offerPage, offerText);
            getOfferInfoFromTable(offerPage, offerText);
            getOfferDescription(offerPage,offerText);

            offersWithDescription.add(offerText.toString());
        }

        return offersWithDescription;
    }

    public void getOfferInfoFromHeader(Document offerPage, StringBuilder offerText){
        Elements offerHeader = offerPage.select("header");

        offerText.append("Nazwa: ");
        offerText.append(offerHeader.select("h1").text());
        offerText.append("\nAdres: ");
        offerText.append(offerHeader.select("a").text());
        offerText.append("\nCena: ");
        offerText.append(offerHeader.select("strong").text());
    }

    public void getOfferInfoFromTable(Document offerPage,  StringBuilder offerText){
        Elements offerInfoDiv = offerPage.select("div");
        List<String> offerInfo = offerInfoDiv.stream().filter(e -> e.className().equals("css-2vlfd7 e10umaf20"))
                .map(Element::children)

                .flatMap(e -> e.select("div").stream().filter(
                        elem -> elem.className().equals("css-1qzszy5 enb64yk2")
                ))
                .map(Element::text)
                .toList();

        for(int i = 0; i < offerInfo.size(); i+=2){
            if (offerInfo.get(i).equals("Rodzaj zabudowy"))
                break;
            offerText.append("\n");
            offerText.append(offerInfo.get(i)).append(": ").append(offerInfo.get(i + 1));
        }
    }

    public void getOfferDescription(Document offerPage, StringBuilder offerText){
        offerText.append("\nOpis oferty: ");
        offerText.append(offerPage.select("div")
                .stream().filter(e -> e.className().equals("css-1wekrze e1lbnp621"))
                .toList().get(0).text());
    }
}
