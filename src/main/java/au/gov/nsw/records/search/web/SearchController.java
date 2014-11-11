package au.gov.nsw.records.search.web;

import au.gov.nsw.records.search.bean.FacetResultItem;
import au.gov.nsw.records.search.bean.SearchResult;
import au.gov.nsw.records.search.bean.SearchResultItem;
import au.gov.nsw.records.search.model.*;
import au.gov.nsw.records.search.service.*;
import org.apache.lucene.facet.search.params.CountFacetRequest;
import org.apache.lucene.facet.search.params.FacetSearchParams;
import org.apache.lucene.facet.taxonomy.CategoryPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

@RequestMapping("/search/**")
@Controller
public class SearchController {

    @Autowired
    private  LuceneService lucene;
    private static List<Class<?>> entitiesList = new ArrayList<Class<?>>(Arrays.asList(new Class<?>[]{Serie.class, Item.class, Activity.class, Functionn.class, Person.class, Agency.class}));

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "search/index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                         @RequestParam(value = "fpage", required = false, defaultValue = "1") Integer fpage,
                         @RequestParam(value = "apage", required = false, defaultValue = "1") Integer apage,
                         @RequestParam(value = "size", required = false, defaultValue = "30") Integer pageSize,
                         @RequestParam(value = "count", required = false, defaultValue = "30") Integer count,
                         @RequestParam(value = "location", required = false) String location,
                         @RequestParam(value = "series", required = false) String series,
                         @RequestParam(value = "from", required = false) String from,
                         @RequestParam(value = "to", required = false) String to,
                         @RequestParam(value = "entities", required = false) String entities,
                         @RequestParam(value = "q", defaultValue = "") String queryText,
                         Model model, HttpServletRequest request) {

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 30;
        }
        if (count != null && !count.equals(new Integer(30))) {
            pageSize = count;
        }

        if (queryText.contains("entities:")) {
            StringTokenizer st = new StringTokenizer(queryText, " ");
            String strippedQueryText = "";
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if (token.startsWith("entities:")) {
                    entities = token.replace("entities:", "");
                } else {
                    strippedQueryText += " " + token;
                }
            }
            queryText = strippedQueryText.trim();
        }

        LuceneSearchParams params = new LuceneSearchParams();
        FacetSearchParams facetParams = new FacetSearchParams();

        facetParams.addFacetRequest(new CountFacetRequest(new CategoryPath("series_id"), 10));
        facetParams.addFacetRequest(new CountFacetRequest(new CategoryPath("series"), 10));
        facetParams.addFacetRequest(new CountFacetRequest(new CategoryPath("location"), 10));
        //facetParams.addFacetRequest(new CountFacetRequest(new CategoryPath("startyear", "endyear"), 10));
        int asize = 15;
        int fsize = 5;

        if (entities != null) {
            List<Class<?>> searchClass = new ArrayList<Class<?>>();
            StringTokenizer st = new StringTokenizer(entities, ",");
            while (st.hasMoreTokens()) {
                String entity = st.nextToken();
                if (entity.length() > 4) {
                    entity = entity.substring(0, 4);
                }
                for (Class<?> cls : entitiesList) {
                    if (cls.getName().toLowerCase().contains(entity.toLowerCase())) {
                        searchClass.add(cls);
                        break;
                    }
                }
            }
            if (searchClass.size() > 0) {
                Class<?>[] clazzParams = new Class<?>[searchClass.size()];
                searchClass.toArray(clazzParams);
                SearchResult customSearch = lucene.search(params.setQuery(queryText).setFacetParams(facetParams).setPage(page).setSize(pageSize).setClazz(clazzParams));

                model.addAttribute("customsearch", customSearch.getResults());
                model.addAttribute("customsearch_total", customSearch.getResultCount());
                model.addAttribute(
                        "customsearch_count",
                        (int)(Math.ceil(customSearch.getResultCount() / Double.valueOf(pageSize)))
                );
            }

        } else {
            SearchResult activitiesFunctions = lucene.search(params.setQuery(queryText).setFacetParams(facetParams).setPage(fpage).setSize(fsize).setClazz(Activity.class, Functionn.class));
            SearchResult agenciesPeople = lucene.search(params.setQuery(queryText).setFacetParams(facetParams).setPage(apage).setSize(asize).setClazz(Agency.class, Person.class));
            SearchResult seriesItems = lucene.search(params.setQuery(queryText).setSeries(series).setLocation(location).setFrom(from).setTo(to).setFacetParams(facetParams).setPage(page).setSize(pageSize).setClazz(Serie.class, Item.class));

            List<FacetResultItem> facets = seriesItems.getFacets();
            for (FacetResultItem fri : facets) {
                if (fri.getLabel().equals("series")) {
                    for (FacetResultItem subFri : fri.getItems()) {
                        subFri.setLabel(Serie.findSerie(Integer.valueOf(subFri.getLabel())).getTitle());
                    }
                }
            }

            model.addAttribute("activitiesfunctions", activitiesFunctions.getResults());
            model.addAttribute("activitiesfunctions_count", Math.ceil(activitiesFunctions.getResultCount() / (double)fsize));


            model.addAttribute("seriesitems", seriesItems.getResults());
            model.addAttribute("seriesitems_count", Math.ceil(seriesItems.getResultCount() / Double.valueOf(pageSize)));
            model.addAttribute("seriesitems_total", seriesItems.getResultCount());

            model.addAttribute("agenciespeoples", agenciesPeople.getResults());
            model.addAttribute("agenciespeoples_count", Math.ceil(agenciesPeople.getResultCount() / (double)asize));

            model.addAttribute("facets", facets);

            // also make it available in custom search
            model.addAttribute("count", seriesItems.getResultCount());

            if (page < Math.ceil(seriesItems.getResultCount() / Double.valueOf(pageSize))) {
                model.addAttribute("next_url", request.getRequestURL() + "?q=" + queryText + "&page=" + (page + 1) + "&size=" + pageSize);
            }
        }
        String nonPageParams = "&q=" + queryText;
        if (location != null) {
            nonPageParams += "&location=" + location;
        }
        if (series != null) {
            nonPageParams += "&series=" + series;
        }
        if (from != null) {
            nonPageParams += "&from=" + from;
        }
        if (to != null) {
            nonPageParams += "&to=" + to;
        }

        model.addAttribute("self_url", request.getRequestURL() + "?q=" + queryText);

        if (page > 1) {
            model.addAttribute("prev_url", request.getRequestURL() + "?q=" + queryText + "&page=" + (page - 1) + "&size=" + pageSize);
        }

        List<SearchResultItem> hotLinks = new ArrayList<SearchResultItem>();

        if (StringService.isNumeric(queryText)) {
            Agency ag = Agency.findAgency(Integer.parseInt(queryText));
            if (ag != null) {
                hotLinks.add(new SearchResultItem("agencies", ag.getTitle(), "", String.valueOf(ag.getId()), "/agencies/" + ag.getId(), true));
            }
            Serie sr = Serie.findSerie(Integer.parseInt(queryText));
            if (sr != null) {
                hotLinks.add(new SearchResultItem("series", sr.getTitle(), "", String.valueOf(sr.getId()), "/series/" + sr.getId(), true));
            }
            Item it = Item.findItem(Integer.parseInt(queryText));
            if (it != null) {
                hotLinks.add(new SearchResultItem("items", it.getTitle(), "", String.valueOf(it.getId()), "/items/" + it.getId(), true));
            }
        }

        model.addAttribute("hotlinks", hotLinks);
        model.addAttribute("q", queryText);

        model.addAttribute("page", page);
        model.addAttribute("fpage", fpage);
        model.addAttribute("apage", apage);
        model.addAttribute("size", pageSize);
        model.addAttribute("asize", asize);
        model.addAttribute("fsize", fsize);

        model.addAttribute("entities", entities);

        model.addAttribute("location", location);
        model.addAttribute("series", series);
        model.addAttribute("from", from);
        model.addAttribute("to", to);

        model.addAttribute("nonPageParams", nonPageParams);

        model.addAttribute("baseurl", request.getRequestURL() + String.format("?q=%s", queryText));
        model.addAttribute("view", "search/list");
        return "search/list";
    }
}
