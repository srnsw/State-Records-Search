package au.gov.nsw.records.search.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import au.gov.nsw.records.search.model.Activity;
import au.gov.nsw.records.search.model.Agency;
import au.gov.nsw.records.search.model.Functionn;
import au.gov.nsw.records.search.model.Item;
import au.gov.nsw.records.search.model.Ministry;
import au.gov.nsw.records.search.model.Organisation;
import au.gov.nsw.records.search.model.Person;
import au.gov.nsw.records.search.model.Portfolio;
import au.gov.nsw.records.search.model.Serie;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<Integer, Person> getIdToPersonConverter() {
        return new org.springframework.core.convert.converter.Converter<Integer, au.gov.nsw.records.search.model.Person>() {
            public au.gov.nsw.records.search.model.Person convert(Integer id) {
                return Person.findPerson(id);
            }
        };
    }

	public Converter<Integer, Activity> getIdToActivityConverter() {
        return new org.springframework.core.convert.converter.Converter<Integer, au.gov.nsw.records.search.model.Activity>() {
            public au.gov.nsw.records.search.model.Activity convert(Integer id) {
                return Activity.findActivity(id);
            }
        };
    }
	
	public Converter<Integer, Serie> getIdToSerieConverter() {
    return new org.springframework.core.convert.converter.Converter<Integer, au.gov.nsw.records.search.model.Serie>() {
        public au.gov.nsw.records.search.model.Serie convert(Integer id) {
        	return Serie.findSerie(id);
        }
    };
}

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }

	public Converter<Serie, String> getSerieToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<au.gov.nsw.records.search.model.Serie, java.lang.String>() {
            public String convert(Serie serie) {
                return new StringBuilder().append(serie.getTitle()).toString();
            }
        };
    }

	public Converter<Integer, Agency> getIdToAgencyConverter() {
        return new org.springframework.core.convert.converter.Converter<Integer, au.gov.nsw.records.search.model.Agency>() {
            public au.gov.nsw.records.search.model.Agency convert(Integer id) {
                return Agency.findAgency(id);
            }
        };
    }

	public Converter<Integer, Item> getIdToItemConverter() {
        return new org.springframework.core.convert.converter.Converter<Integer, au.gov.nsw.records.search.model.Item>() {
            public au.gov.nsw.records.search.model.Item convert(Integer id) {
                return Item.findItem(id);
            }
        };
    }

	public Converter<Integer, Portfolio> getIdToPortfolioConverter() {
        return new org.springframework.core.convert.converter.Converter<Integer, au.gov.nsw.records.search.model.Portfolio>() {
            public au.gov.nsw.records.search.model.Portfolio convert(Integer id) {
                return Portfolio.findPortfolio(id);
            }
        };
    }

	public Converter<Integer, Ministry> getIdToMinistryConverter() {
        return new org.springframework.core.convert.converter.Converter<Integer, au.gov.nsw.records.search.model.Ministry>() {
            public au.gov.nsw.records.search.model.Ministry convert(Integer id) {
                return Ministry.findMinistry(id);
            }
        };
    }

	public Converter<Integer, Functionn> getIdToFunctionnConverter() {
        return new org.springframework.core.convert.converter.Converter<Integer, au.gov.nsw.records.search.model.Functionn>() {
            public au.gov.nsw.records.search.model.Functionn convert(Integer id) {
                return Functionn.findFunctionn(id);
            }
        };
    }

	public Converter<Integer, Organisation> getIdToOrganisationConverter() {
        return new org.springframework.core.convert.converter.Converter<Integer, au.gov.nsw.records.search.model.Organisation>() {
            public au.gov.nsw.records.search.model.Organisation convert(Integer id) {
                return Organisation.findOrganisation(id);
            }
        };
    }
}
