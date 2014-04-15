package au.gov.nsw.records.search.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;


/**
 * Created by archerd on 14/02/14.
 */
@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class AgencyTest {

    @Test
    public void  soup(){
        String html ="<ol id=\"agencyTitles\"><li ><name>Name 1</name><start>1788</start><end>2014</end></li><li><name>Name 2</name><start>2014</start><end>3000</end></li>";
        Document document = Jsoup.parse(html);
        Elements e = document.select("ol#agencyTitles li:first-child");

        for(Element el : e ){
           Elements name = el.select("name");

            String text = name.text();
            text.toString();
        }

        e.toString();
        document.toString();
    }

    @Test
    public void getNames() throws Exception {
        Agency agency = new Agency();
        List<Object> names;
        agency.setTitle("");
        assertThat(agency.getNames(), hasSize(0));

        agency.setTitle("Office of Internal Affairs (c.1994-1999) / Special Crime and Internal Affairs Command (1999-2003) [New South Wales Police Service]");
        names = agency.getNames();
        assertThat(names, hasSize(2));


        agency.setTitle("Air Pollution Control Branch (1962 – c.1975) / Clean Air Branch (c.1975 – c. 1989) / Air Branch (c. 1989 – c.1992) [State Pollution Control Commission]");
        names = agency.getNames();
        assertThat(agency.getNames(), hasSize(3));

        agency.setTitle("Public Employment Industrial Relations Authority [Division] (by 1989-by 1990) Public Employment Industrial Relations Service [Division] (by 1990-by 1994) Public Employer Service Branch (by 1994-1996) Employee Relations Division (1996-2003)");
        names = agency.getNames();
        assertThat(agency.getNames(), hasSize(4));

        agency.setTitle("Royal Commission of Inquiry on the Landlord and Tenant (Amendment) Act, 1948, as amended");
        names = agency.getNames();
        assertThat(agency.getNames(), hasSize(1));

        agency.setTitle("Royal Commission of Inquiry into the Charges against Mr E.M.G. Eddy, the Chief Commissioner of Railways, preferred by Mr W.F.Schey, M.L.A., in the Legislative Assembly on the 9th March, 1892");
        names = agency.getNames();
        assertThat(agency.getNames(), hasSize(1));

        agency.setTitle("Combaning Provisional School [I] (1899-1906) Combaning Public School [I] (1906-1917) Combaning Half-time School (1917-1920)\n" +
                "Combaning Provisional School [II] (1922-1930) Combaning South Provisional School (1930-1952) Combaning South Public School (1952-1984)\n" +
                "Jingellic East Provisional School (1883-1884) Jingellic East Public School (1884-1891) Jingellic East Half-time School (1891-1893)\n" +
                "Jingellic Provisional School [I] (1893-1894) Jingellic Public School [I] (1894-1910)\n" +
                "Jingellic Half-time School [I] (1910-1913) Jingelic Provisional School [II] (1913-1914)\n" +
                "Jingellic Half-time School [II]");
        names = agency.getNames();
        assertThat(agency.getNames(), hasSize(14));

        agency.setTitle("National Works Branch (under section 537 of the Local Government Act) [Department of Works and Local Government]");
        names = agency.getNames();
        assertThat(agency.getNames(), hasSize(1));

        agency.setTitle("Open Training and Education Network (OTEN) - Distance Education\n" +
                "NSW Health Department (1982-2009) Department of Health (2009-2011) Ministry of Health (2011- )\n" +
                "Newcastle Asylum for Imbeciles and Idiots (1871-1914) / Newcastle Mental Hospital (1915-?69) / Newcastle Psychiatric Centre (?1969- ) / James Fletcher Hospital (?- )\n" +
                "Coalfields Branch (by 1894–1981) / Coalfields Inspection Branch (c.1981 – c.1989) / Coal Mining Inspectorate and Engineering Branch (c.1989-1999) [Mineral Resources]\n" +
                "Registrar and Inquiry Branch (by1898 - by1976) / Titles Branch (by1976 - 2004) / Mining Titles (2004 - ) [Mineral Resources]\n" +
                "Free Public Library (1869-1899) Public Library of New South Wales (1899-1969) Library of New South Wales(1969-1975) State Library of New South Wales (1975- )\n" +
                "State Children Relief Dept (1881-1923) Child Welfare Dept (1923-70) Dept of Child Welfare and Social Welfare (1970-73) Youth and Community Services (1973-75,76-88) Youth, Ethnic and Community Affairs (1975-76) Dept of Community Services (1992-2009)\n" +
                "Aleck Town (goldfields) Police Station (1889 - 1894) / Alectown Police Station [1] (1894-1930)");
        names = agency.getNames();
        assertThat(agency.getNames(), hasSize(26));

        agency.setTitle("Colonial Museum (1827 - 1834) / Australian Museum (1834 - )\n" +
                "\n" +
                "Sydney Observatory\n" +
                "\n" +
                "National Fitness and Recreation Service of New South Wales(1970-1972) Sport and Recreation Service of New South Wales (1972-1975)\n" +
                "\n" +
                "History Unit [Department of School Education]\n" +
                "\n" +
                "Tuggerah Public School");
        names = agency.getNames();
        assertThat(agency.getNames(), hasSize(7));
    }
}
