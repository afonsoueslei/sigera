package br.ufg.inf.sigera.controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@RequestScoped
public class RssBean {

    public RssBean() {
        getrss();
    }
    List<RssEntry> feeds;

    public List<RssEntry> getFeed() {
        return feeds;
    }

    private void getrss() {

        String rssFeedUrl = "http://inf.ufg.br/rss.xml";
        // número desejado de feeds para recuperar
        int count = 10;  

        SimpleDateFormat df = new SimpleDateFormat("E dd 'de' MMM 'de' YYYY", new Locale("pt", "BR"));

        try {
            // Connect
            URLConnection feedUrl = new URL(rssFeedUrl).openConnection();
            SyndFeedInput input = new SyndFeedInput();
            // Build the feed list
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            List<SyndEntry> feedList = feed.getEntries();
            int feedSize = feedList.size();

            // Salva o número desejado de feeds caso tenha recuperado mais 
            if (feedSize > count) {
                feedSize = count;
            }

            feeds = new ArrayList<RssEntry>();

            for (int i = 0; i < feedSize; i++) {

                SyndEntry entry = (SyndEntry) feedList.get(i);

                RssEntry rss = new RssEntry();

                // Formato baseado em meus critérios
                rss.setLink(entry.getLink());
                rss.setTitulo(entry.getTitle());
                rss.setDataPublicacao(df.format(entry.getPublishedDate()));
                rss.setDescricao(entry.getDescription().getValue().substring(0, 100));

                feeds.add(rss);
            }

        } catch (Exception e) {

            // Para qualquer outro comportamento inesperado
            feeds = new ArrayList<RssEntry>();
            RssEntry rss = new RssEntry();
            rss.setTitulo("Feed não pode ser recuperado!");
            rss.setDescricao(e.getMessage());
            feeds.add(rss);
            Logger.getLogger(RssBean.class
                    .getName()).log(Level.SEVERE, null, e);
        }

    }

    // List item class
    public class RssEntry {

        private String autor;
        private String link;
        private String titulo;
        private String descricao;
        private String dataPublicacao;

        public RssEntry() {
        }

        public String getAutor() {
            return autor;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public String getDataPublicacao() {
            return dataPublicacao;
        }

        public void setDataPublicacao(String dataPublicacao) {
            this.dataPublicacao = dataPublicacao;
        }
    }
}
