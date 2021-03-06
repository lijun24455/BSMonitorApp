package gmcc.bsmonitor.views.districtpicker.service;



import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import gmcc.bsmonitor.views.districtpicker.model.CityModel;
import gmcc.bsmonitor.views.districtpicker.model.DistrictModel;
import gmcc.bsmonitor.views.districtpicker.model.ProvinceModel;


public class XmlParserHandler extends DefaultHandler {

	private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();

	private List<CityModel> cityList = new ArrayList<CityModel>();
	 	  
	public XmlParserHandler() {
		
	}

	public List<CityModel> getDataList() {
		return cityList;
	}

	@Override
	public void startDocument() throws SAXException {
	}

//	ProvinceModel provinceModel = new ProvinceModel();
	CityModel cityModel = new CityModel();
	DistrictModel districtModel = new DistrictModel();
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
//		if (qName.equals("province")) {
//			provinceModel = new ProvinceModel();
//			provinceModel.setName(attributes.getValue(0));
//			provinceModel.setCityList(new ArrayList<CityModel>());
//		} else
		if (qName.equals("city")) {
			cityModel = new CityModel();
			cityModel.setName(attributes.getValue(0));
			cityModel.setDistrictList(new ArrayList<DistrictModel>());
		} else if (qName.equals("district")) {
			districtModel = new DistrictModel();
			districtModel.setName(attributes.getValue(0));
			districtModel.setZipcode(attributes.getValue(1));
		}
	}


	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("district")) {
			cityModel.getDistrictList().add(districtModel);
        } else if (qName.equals("city")) {
//        	provinceModel.getCityList().add(cityModel);
			cityList.add(cityModel);
        }
//		else if (qName.equals("province")) {
//        	provinceList.add(provinceModel);
//        }
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

}
