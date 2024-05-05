import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.OtodomParser;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try{
//            Document otodomPage = Jsoup.connect("https://www.otodom.pl/pl/wyniki/wynajem/mieszkanie,2-pokoje/mazowieckie/warszawa/warszawa/warszawa?limit=72&daysSinceCreated=1&by=LATEST&direction=DESC&viewType=listing")
//                    .get();
//
//            Elements ulElements = otodomPage.select("ul");
//
//            Set<String> hrefOfferSet = ulElements.stream()
//                    .filter(e -> e.className().equals("css-rqwdxd e1tno8ef0"))
//                    .flatMap(e -> e.select("a")
//                            .stream()
//                            .map(el -> el.attribute("href").getValue())) // Use flatMap to flatten nested streams
//                    .collect(Collectors.toSet());
//
//
//            for (String href :
//                    hrefOfferSet) {
//                Document offerPage = Jsoup.connect("https://www.otodom.pl" + href)
//                        .get();
//                Elements offerHeader = offerPage.select("header");
//                StringBuilder offerText = new StringBuilder();
//
//                offerText.append("Nazwa: ");
//                offerText.append(offerHeader.select("h1").text());
//                offerText.append("\nAdres: ");
//                offerText.append(offerHeader.select("a").text());
//                offerText.append("\nCena: ");
//                offerText.append(offerHeader.select("strong").text());
//
//                Elements offerInfoDiv = offerPage.select("div");
//                List<String> offerInfo = offerInfoDiv.stream().filter(e -> e.className().equals("css-2vlfd7 e10umaf20"))
//                        .map(Element::children)
//
//                        .flatMap(e -> e.select("div").stream().filter(
//                                elem -> elem.className().equals("css-1qzszy5 enb64yk2")
//                        ))
//                        .map(Element::text)
//                        .toList();
//
//                for(int i = 0; i < offerInfo.size(); i+=2){
//                    if (offerInfo.get(i).equals("Rodzaj zabudowy"))
//                        break;
//                    offerText.append("\n");
//                    offerText.append(offerInfo.get(i)).append(": ").append(offerInfo.get(i + 1));
//                }
//
//                Element offerDesc = offerPage.select("div")
//                        .stream().filter(e -> e.className().equals("css-1wekrze e1lbnp621"))
//                        .toList().get(0);
//                System.out.println(offerText);
//                System.out.println();
//            }

            OtodomParser parser = new OtodomParser("https://www.otodom.pl/pl/wyniki/wynajem/mieszkanie,2-pokoje/mazowieckie/warszawa/warszawa/warszawa?limit=72&daysSinceCreated=1&by=LATEST&direction=DESC&viewType=listing");
            parser.getOfferDescriptionFromUrl(parser.getOffersUrls());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
