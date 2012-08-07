package eu.cec.sanco.utils.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.cec.sanco.beans.AppState;
import eu.cec.sanco.utils.api.Utils;

@Scope("singleton")
@Component("utils")
public class UtilsImpl implements Utils {

  @Autowired
  private AppState appState;

  private static char[] ALL_CHARS = null;

  private static final Random RANDOM = new Random(System.currentTimeMillis());

  public UtilsImpl() {

  }

  public String rebuildString(String str) {
    String[] split = str.replaceAll("[^A-Za-z]", " ").split("\\s");
    StringBuilder ret = new StringBuilder();
    List<String> tmp = new ArrayList<String>(split.length);

    for (String s : split) {
      if (!StringUtils.isEmpty(s.trim())) {
        tmp.add(s.trim().toLowerCase());
      }
    }

    for (int i = 0; i < tmp.size(); i++) {
      ret.append(tmp.get(i));
      if (i < (tmp.size() - 1)) {
        ret.append('.');
      }
    }

    return ret.toString();
  }

  public Date getNow() {
    return new Date();
  }

  public List<SelectItem> getSectors() {
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle appmsgBundle = context.getApplication().getResourceBundle(context, "appmsg");

    ArrayList<SelectItem> sectors = new ArrayList<SelectItem>(20);
    sectors.add(new SelectItem("Consumer Goods", appmsgBundle.getString("sectors.consumer.goods.value")));
    sectors.add(new SelectItem("General Consumer Services", appmsgBundle
        .getString("sectors.general.consumer.services.value")));
    sectors.add(new SelectItem("Financial Services", appmsgBundle.getString("sectors.financial.services.value")));
    sectors.add(new SelectItem("Postal services and electronic communications", appmsgBundle
        .getString("sectors.postal.services.and.electronic.communications.value")));
    sectors.add(new SelectItem("Transport services", appmsgBundle.getString("sectors.transport.services.value")));
    sectors.add(new SelectItem("Leisure Services", appmsgBundle.getString("sectors.leisure.services.value")));
    sectors.add(new SelectItem("Energy and Water", appmsgBundle.getString("sectors.energy.and.water.value")));
    sectors.add(new SelectItem("Health", appmsgBundle.getString("sectors.health.value")));
    sectors.add(new SelectItem("Education", appmsgBundle.getString("sectors.education.value")));
    sectors.add(new SelectItem("Other", appmsgBundle.getString("sectors.other.value")));

    return sectors;
  }

  public List<SelectItem> getMarkets(String sector) {

    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle appmsgBundle = context.getApplication().getResourceBundle(context, "appmsg");

    ArrayList<SelectItem> markets = new ArrayList<SelectItem>(20);

    if (StringUtils.isEmpty(sector) || sector.equals("Consumer Goods")) {
      markets.add(new SelectItem("Food - Fruit and vegetables", appmsgBundle
          .getString("sectors.consumer.goods.food.fruit.and.vegetables")));
      markets.add(new SelectItem("Food - Meat", appmsgBundle.getString("sectors.consumer.goods.food.meat")));
      markets.add(new SelectItem("Food - Bread and Cereals", appmsgBundle
          .getString("sectors.consumer.goods.food.bread.and.cereals")));
      markets.add(new SelectItem("Food - Health food and nutrients", appmsgBundle
          .getString("sectors.consumer.goods.food.health.food.and.nutrients")));
      markets.add(new SelectItem("Food - Other", appmsgBundle.getString("sectors.consumer.goods.food.other")));
      markets.add(new SelectItem("Non-alcoholic beverages", appmsgBundle
          .getString("sectors.consumer.goods.non.alcoholic.beverages")));
      markets.add(new SelectItem("Alcoholic beverages", appmsgBundle
          .getString("sectors.consumer.goods.alcoholic.beverages")));
      markets.add(new SelectItem("Tobacco", appmsgBundle.getString("sectors.consumer.goods.tobacco")));
      markets.add(new SelectItem("Clothing (including tailor-made goods) and footwear", appmsgBundle
          .getString("sectors.consumer.goods.clothing.including.tailor.made.goods.and.footwear")));
      markets.add(new SelectItem("House maintenance and improvement goods", appmsgBundle
          .getString("sectors.consumer.goods.house.maintenance.and.improvement.goods")));
      markets.add(new SelectItem("Furnishings", appmsgBundle.getString("sectors.consumer.goods.furnishings")));
      markets
          .add(new SelectItem(
              "Large domestic household appliances (including vacuum cleaners and microwaves)",
              appmsgBundle
                  .getString("sectors.consumer.goods.large.domestic.household.appliances.including.vacuum.cleaners.and.microwaves")));
      markets
          .add(new SelectItem(
              "Small domestic household appliances (including coffee machines and food- processing appliances)",
              appmsgBundle
                  .getString("sectors.consumer.goods.small.domestic.household.appliances.including.coffee.machines.and.food.processing.appliances")));
      markets.add(new SelectItem("Electronic goods (non-ICT/recreational)", appmsgBundle
          .getString("sectors.consumer.goods.electronic.goods.non.ict.recreational")));
      markets.add(new SelectItem("Information and communication technology (ICT) goods", appmsgBundle
          .getString("sectors.consumer.goods.information.and.communication.technology.ict.goods")));
      markets.add(new SelectItem("Leisure goods (sports equipment, musical instruments, etc)", appmsgBundle
          .getString("sectors.consumer.goods.leisure.goods.sports.equipment.musical.instruments.etc")));
      markets.add(new SelectItem("New cars", appmsgBundle.getString("sectors.consumer.goods.new.cars")));
      markets
          .add(new SelectItem("Second-hand cars", appmsgBundle.getString("sectors.consumer.goods.second.hand.cars")));
      markets.add(new SelectItem("Other personal transport", appmsgBundle
          .getString("sectors.consumer.goods.other.personal.transport")));
      markets
          .add(new SelectItem(
              "Spares and accessories for vehicles and other means of personal transport",
              appmsgBundle
                  .getString("sectors.consumer.goods.spares.and.accessories.for.vehicles.and.other.means.of.personal.transport")));
      markets
          .add(new SelectItem(
              "Fuels and lubricants for vehicles and other means of personal transport",
              appmsgBundle
                  .getString("sectors.consumer.goods.fuels.and.lubricants.for.vehicles.and.other.means.of.personal.transport")));
      markets.add(new SelectItem("Books, magazines, newspapers, stationery (excluding postal delivery)", appmsgBundle
          .getString("sectors.consumer.goods.books.magazines.newspapers.stationery.excluding.postal.delivery")));
      markets.add(new SelectItem("Pets and pet goods", appmsgBundle
          .getString("sectors.consumer.goods.pets.and.pet.goods")));
      markets.add(new SelectItem("Electrical appliances for personal care", appmsgBundle
          .getString("sectors.consumer.goods.electrical.appliances.for.personal.care")));
      markets.add(new SelectItem("Cosmetics and toiletries for personal care", appmsgBundle
          .getString("sectors.consumer.goods.cosmetics.and.toiletries.for.personal.care")));
      markets.add(new SelectItem("Jewellery, silverware, clocks, watches and accessories", appmsgBundle
          .getString("sectors.consumer.goods.jewellery.silverware.clocks.watches.and.accessories")));
      markets.add(new SelectItem("Baby and child care articles", appmsgBundle
          .getString("sectors.consumer.goods.baby.and.child.care.articles")));
      markets
          .add(new SelectItem(
              "Cleaning and maintenance products, articles for cleaning and non-durable household articles",
              appmsgBundle
                  .getString("sectors.consumer.goods.cleaning.and.maintenance.products.articles.for.cleaning.and.non.durable.household.articles")));
      markets.add(new SelectItem("Other", appmsgBundle.getString("sectors.consumer.goods.other")));
    }

    else if (sector.equals("General Consumer Services")) {

      markets.add(new SelectItem("Real estate services", appmsgBundle
          .getString("sectors.general.consumer.services.real.estate.services")));
      markets.add(new SelectItem("Construction of new houses", appmsgBundle
          .getString("sectors.general.consumer.services.construction.of.new.houses")));
      markets.add(new SelectItem("House maintenance and improvement services", appmsgBundle
          .getString("sectors.general.consumer.services.house.maintenance.and.improvement.services")));
      markets.add(new SelectItem("House removal and storage", appmsgBundle
          .getString("sectors.general.consumer.services.house.removal.and.storage")));
      markets.add(new SelectItem("House cleaning services", appmsgBundle
          .getString("sectors.general.consumer.services.house.cleaning.services")));
      markets.add(new SelectItem("Personal care services", appmsgBundle
          .getString("sectors.general.consumer.services.personal.care.services")));
      markets.add(new SelectItem("Cleaning, repair and hiring of clothing and footwear", appmsgBundle
          .getString("sectors.general.consumer.services.cleaning.repair.and.hiring.of.clothing.and.footwear")));
      markets.add(new SelectItem("Support, research and intermediary services", appmsgBundle
          .getString("sectors.general.consumer.services.support.research.and.intermediary.services")));
      markets.add(new SelectItem("Maintenance and repair of vehicles and other transport", appmsgBundle
          .getString("sectors.general.consumer.services.maintenance.and.repair.of.vehicles.and.other.transport")));
      markets.add(new SelectItem("Legal services & accountancy", appmsgBundle
          .getString("sectors.general.consumer.services.legal.services.accountancy")));
      markets.add(new SelectItem("Funeral services", appmsgBundle
          .getString("sectors.general.consumer.services.funeral.services")));
      markets.add(new SelectItem("Child care", appmsgBundle.getString("sectors.general.consumer.services.child.care")));
      markets.add(new SelectItem("Pet services", appmsgBundle
          .getString("sectors.general.consumer.services.pet.services")));
      markets.add(new SelectItem("Other", appmsgBundle.getString("sectors.general.consumer.services.other")));

    }

    else if (sector.equals("Financial Services")) {

      markets.add(new SelectItem("Financial Services - Payment account and payment services", appmsgBundle
          .getString("sectors.financial.services.financial.services.payment.account.and.payment.services")));
      markets.add(new SelectItem("Financial Services - Credit (excluding mortgage/home loans)", appmsgBundle
          .getString("sectors.financial.services.financial.services.credit.excluding.mortgage.home.loans")));
      markets.add(new SelectItem("Financial Services - Mortgages / Home loans", appmsgBundle
          .getString("sectors.financial.services.financial.services.mortgages.home.loans")));
      markets.add(new SelectItem("Financial Services - Savings", appmsgBundle
          .getString("sectors.financial.services.financial.services.savings")));
      markets.add(new SelectItem("Financial Services - Other", appmsgBundle
          .getString("sectors.financial.services.financial.services.other")));
      markets.add(new SelectItem("Investments, pensions and securities", appmsgBundle
          .getString("sectors.financial.services.investments.pensions.and.securities")));
      markets.add(new SelectItem("Non-life Insurance - Home and property", appmsgBundle
          .getString("sectors.financial.services.non.life.insurance.home.and.property")));
      markets.add(new SelectItem("Non-life Insurance - Transport", appmsgBundle
          .getString("sectors.financial.services.non.life.insurance.transport")));
      markets.add(new SelectItem("Non-life Insurance - Travel", appmsgBundle
          .getString("sectors.financial.services.non.life.insurance.travel")));
      markets.add(new SelectItem("Non-life Insurance - Health, accident and other", appmsgBundle
          .getString("sectors.financial.services.non.life.insurance.health.accident.and.other")));
      markets.add(new SelectItem("Insurance - Life", appmsgBundle
          .getString("sectors.financial.services.insurance.life")));
      markets.add(new SelectItem("Other", appmsgBundle.getString("sectors.financial.services.other")));

    }

    else if (sector.equals("Postal services and electronic communications")) {

      markets.add(new SelectItem("Postal services & couriers", appmsgBundle
          .getString("sectors.postal.services.and.electronic.communications.postal.services.couriers")));
      markets.add(new SelectItem("Fixed telephone services", appmsgBundle
          .getString("sectors.postal.services.and.electronic.communications.fixed.telephone.services")));
      markets.add(new SelectItem("Mobile telephone services", appmsgBundle
          .getString("sectors.postal.services.and.electronic.communications.mobile.telephone.services")));
      markets.add(new SelectItem("Internet services", appmsgBundle
          .getString("sectors.postal.services.and.electronic.communications.internet.services")));
      markets.add(new SelectItem("Television services", appmsgBundle
          .getString("sectors.postal.services.and.electronic.communications.television.services")));
      markets.add(new SelectItem("Other communication services", appmsgBundle
          .getString("sectors.postal.services.and.electronic.communications.other.communication.services")));
    }

    else if (sector.equals("Transport services")) {

      markets.add(new SelectItem("Tram, bus, metro and underground", appmsgBundle
          .getString("sectors.transport.services.tram.bus.metro.and.underground")));
      markets.add(new SelectItem("Railways", appmsgBundle.getString("sectors.transport.services.railways")));
      markets.add(new SelectItem("Airlines", appmsgBundle.getString("sectors.transport.services.airlines")));
      markets.add(new SelectItem("Taxi", appmsgBundle.getString("sectors.transport.services.taxi")));
      markets.add(new SelectItem("Sea, river, other water transport", appmsgBundle
          .getString("sectors.transport.services.sea.river.other.water.transport")));
      markets.add(new SelectItem("Transport infrastructure services", appmsgBundle
          .getString("sectors.transport.services.transport.infrastructure.services")));
      markets.add(new SelectItem("Rental services", appmsgBundle
          .getString("sectors.transport.services.rental.services")));
      markets.add(new SelectItem("Other", appmsgBundle.getString("sectors.transport.services.other")));

    }

    else if (sector.equals("Leisure Services")) {

      markets.add(new SelectItem("Hotels and other holiday accommodation", appmsgBundle
          .getString("sectors.leisure.services.hotels.and.other.holiday.accommodation")));
      markets.add(new SelectItem("Package travel", appmsgBundle.getString("sectors.leisure.services.package.travel")));
      markets.add(new SelectItem("Travel agency services", appmsgBundle
          .getString("sectors.leisure.services.travel.agency.services")));
      markets.add(new SelectItem("Timeshare and similar", appmsgBundle
          .getString("sectors.leisure.services.timeshare.and.similar")));
      markets.add(new SelectItem("Restaurants and bars", appmsgBundle
          .getString("sectors.leisure.services.restaurants.and.bars")));
      markets.add(new SelectItem("Services related to sports and hobbies", appmsgBundle
          .getString("sectors.leisure.services.services.related.to.sports.and.hobbies")));
      markets.add(new SelectItem("Cultural and entertainment services", appmsgBundle
          .getString("sectors.leisure.services.cultural.and.entertainment.services")));
      markets.add(new SelectItem("Gambling, lotteries", appmsgBundle
          .getString("sectors.leisure.services.gambling.lotteries")));
      markets.add(new SelectItem("Other leisure services", appmsgBundle
          .getString("sectors.leisure.services.other.leisure.services")));

    }

    else if (sector.equals("Energy and Water")) {

      markets.add(new SelectItem("Water", appmsgBundle.getString("sectors.energy.and.water.water")));
      markets.add(new SelectItem("Electricity", appmsgBundle.getString("sectors.energy.and.water.electricity")));
      markets.add(new SelectItem("Gas", appmsgBundle.getString("sectors.energy.and.water.gas")));
      markets.add(new SelectItem("Other energy sources", appmsgBundle
          .getString("sectors.energy.and.water.other.energy.sources")));

    }

    else if (sector.equals("Health")) {

      markets.add(new SelectItem("Prescribed medication", appmsgBundle
          .getString("sectors.health.prescribed.medication")));
      markets.add(new SelectItem("Over-the-counter medication", appmsgBundle
          .getString("sectors.health.over.the.counter.medication")));
      markets.add(new SelectItem("Medical devices and other physical aids used by patients", appmsgBundle
          .getString("sectors.health.medical.devices.and.other.physical.aids.used.by.patients")));
      markets.add(new SelectItem("Health services", appmsgBundle.getString("sectors.health.health.services")));
      markets.add(new SelectItem("Retirement homes and home care", appmsgBundle
          .getString("sectors.health.retirement.homes.and.home.care")));
      markets.add(new SelectItem("Other", appmsgBundle.getString("sectors.health.other")));

    }

    else if (sector.equals("Education")) {
      markets.add(new SelectItem("Schools", appmsgBundle.getString("sectors.education.schools")));
      markets.add(new SelectItem("Language, driving instruction and other private courses", appmsgBundle
          .getString("sectors.education.language.driving.instruction.and.other.private.courses")));
      markets.add(new SelectItem("Other", appmsgBundle.getString("sectors.education.other")));
    }

    else if (sector.equals("Other")) {
      markets.add(new SelectItem("Other (Includes both goods and services)", appmsgBundle
          .getString("sectors.other.other.includes.both.goods.and.services")));
    }

    return markets;
  }

  public List<SelectItem> getSellingMethods() {
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle appmsgBundle = context.getApplication().getResourceBundle(context, "appmsg");

    ArrayList<SelectItem> sellingMethods = new ArrayList<SelectItem>(20);
    sellingMethods.add(new SelectItem("Face-to-face", appmsgBundle.getString("selling.method.face.to.face")));
    sellingMethods.add(new SelectItem("Face-to-face: Supermarket, hypermarket", appmsgBundle
        .getString("selling.method.face.to.face.supermarket.hypermarket")));
    sellingMethods.add(new SelectItem("Face-to-face: Discount store", appmsgBundle
        .getString("selling.method.face.to.face.discount.store")));
    sellingMethods.add(new SelectItem("Face-to-face: Department store", appmsgBundle
        .getString("selling.method.face.to.face.department.store")));
    sellingMethods.add(new SelectItem("Face-to-face: Retail chain store", appmsgBundle
        .getString("selling.method.face.to.face.retail.chain.store")));
    sellingMethods.add(new SelectItem("Face-to-face: Retail store", appmsgBundle
        .getString("selling.method.face.to.face.retail.store")));
    sellingMethods.add(new SelectItem("Face-to-face: Small shop, small store", appmsgBundle
        .getString("selling.method.face.to.face.small.shop.small.store")));
    sellingMethods.add(new SelectItem("Face-to-face: Greengrocer, night shop", appmsgBundle
        .getString("selling.method.face.to.face.greengrocer.night.shop")));
    sellingMethods.add(new SelectItem("Face-to-face: Street market, farm shop", appmsgBundle
        .getString("selling.method.face.to.face.street.market.farm.shop")));
    sellingMethods.add(new SelectItem("Face-to-face: Petrol station", appmsgBundle
        .getString("selling.method.face.to.face.petrol.station")));
    sellingMethods.add(new SelectItem("Face-to-face: Office (includes banks and other financial institutions)",
        appmsgBundle.getString("selling.method.face.to.face.office.includes.banks.and.other.financial.institutions")));
    sellingMethods.add(new SelectItem("Face-to-face: Travel agency, tour operator agency", appmsgBundle
        .getString("selling.method.face.to.face.travel.agency.tour.operator.agency")));
    sellingMethods.add(new SelectItem("Face-to-face: Hospital, clinic, surgery", appmsgBundle
        .getString("selling.method.face.to.face.hospital.clinic.surgery")));
    sellingMethods.add(new SelectItem("Face-to-face: School", appmsgBundle
        .getString("selling.method.face.to.face.school")));
    sellingMethods.add(new SelectItem("Face-to-face: Sports and leisure premises", appmsgBundle
        .getString("selling.method.face.to.face.sports.and.leisure.premises")));
    sellingMethods.add(new SelectItem("Face-to-face: Other type of premises", appmsgBundle
        .getString("selling.method.face.to.face.other.type.of.premises")));
    sellingMethods
        .add(new SelectItem(
            "Distance selling (e.g. phone, post), excluding e-commerce, mobile commerce and internet auctions",
            appmsgBundle
                .getString("selling.method.distance.selling.e.g.phone.post.excluding.e.commerce.mobile.commerce.and.internet.auctions")));
    sellingMethods.add(new SelectItem("E-commerce, excluding mobile commerce and internet auctions", appmsgBundle
        .getString("selling.method.e.commerce.excluding.mobile.commerce.and.internet.auctions")));
    sellingMethods.add(new SelectItem("Mobile commerce", appmsgBundle.getString("selling.method.mobile.commerce")));
    sellingMethods
        .add(new SelectItem("Market, trade fair", appmsgBundle.getString("selling.method.market.trade.fair")));
    sellingMethods.add(new SelectItem("Auctions", appmsgBundle.getString("selling.method.auctions")));
    sellingMethods.add(new SelectItem("Internet auctions", appmsgBundle.getString("selling.method.internet.auctions")));
    sellingMethods.add(new SelectItem("Selling away from business premises (off-premises)", appmsgBundle
        .getString("selling.method.selling.away.from.business.premises.off.premises")));
    sellingMethods.add(new SelectItem("Other selling methods", appmsgBundle
        .getString("selling.method.other.selling.methods")));
    sellingMethods.add(new SelectItem("Do not know", appmsgBundle.getString("selling.method.do.not.know")));
    sellingMethods.add(new SelectItem("Not applicable", appmsgBundle.getString("selling.method.not.applicable")));

    return sellingMethods;
  }

  public List<SelectItem> getAdvertisingMethods() {
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle appmsgBundle = context.getApplication().getResourceBundle(context, "appmsg");

    ArrayList<SelectItem> advertisingMethods = new ArrayList<SelectItem>(20);

    advertisingMethods.add(new SelectItem("Face-to-face", appmsgBundle.getString("advertising.method.face.to.face")));
    advertisingMethods.add(new SelectItem("Phone call", appmsgBundle.getString("advertising.method.phone.call")));
    advertisingMethods.add(new SelectItem("Text message", appmsgBundle.getString("advertising.method.text.message")));
    advertisingMethods.add(new SelectItem("Audiovisual (TV, etc.)", appmsgBundle
        .getString("advertising.method.audiovisual.tv.etc")));
    advertisingMethods.add(new SelectItem("Print (newspaper, brochure, flyer, etc.)", appmsgBundle
        .getString("advertising.method.print.newspaper.brochure.flyer.etc")));
    advertisingMethods.add(new SelectItem("Internet (website)", appmsgBundle
        .getString("advertising.method.internet.website")));
    advertisingMethods.add(new SelectItem("E-mail", appmsgBundle.getString("advertising.method.e.mail")));
    advertisingMethods.add(new SelectItem("Radio", appmsgBundle.getString("advertising.method.radio")));
    advertisingMethods
        .add(new SelectItem(
            "Outdoor (fixed advertising such as on billboards or mobile advertising such as on vehicles)",
            appmsgBundle
                .getString("advertising.method.outdoor.fixed.advertising.such.as.on.billboards.or.mobile.advertising.such.as.on.vehicles")));
    advertisingMethods.add(new SelectItem("Other", appmsgBundle.getString("advertising.method.other")));
    advertisingMethods.add(new SelectItem("Do not know", appmsgBundle.getString("advertising.method.do.not.know")));
    advertisingMethods
        .add(new SelectItem("Not applicable", appmsgBundle.getString("advertising.method.not.applicable")));

    return advertisingMethods;
  }

  public List<SelectItem> getAvailableCountries() {
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle appmsgBundle = context.getApplication().getResourceBundle(context, "appmsg");
    // if (availableCountries == null) {
    List<SelectItem> availableCountries = new ArrayList<SelectItem>(20);

    availableCountries.add(new SelectItem("AT", appmsgBundle.getString("country.name.at")));
    availableCountries.add(new SelectItem("BE", appmsgBundle.getString("country.name.be")));
    availableCountries.add(new SelectItem("BG", appmsgBundle.getString("country.name.bg")));
    availableCountries.add(new SelectItem("CY", appmsgBundle.getString("country.name.cy")));
    availableCountries.add(new SelectItem("CZ", appmsgBundle.getString("country.name.cz")));
    availableCountries.add(new SelectItem("DK", appmsgBundle.getString("country.name.dk")));
    availableCountries.add(new SelectItem("EE", appmsgBundle.getString("country.name.ee")));
    availableCountries.add(new SelectItem("FI", appmsgBundle.getString("country.name.fi")));
    availableCountries.add(new SelectItem("FR", appmsgBundle.getString("country.name.fr")));
    availableCountries.add(new SelectItem("DE", appmsgBundle.getString("country.name.de")));
    availableCountries.add(new SelectItem("GR", appmsgBundle.getString("country.name.gr")));
    availableCountries.add(new SelectItem("HU", appmsgBundle.getString("country.name.hu")));
    availableCountries.add(new SelectItem("IS", appmsgBundle.getString("country.name.is")));
    availableCountries.add(new SelectItem("IE", appmsgBundle.getString("country.name.ie")));
    availableCountries.add(new SelectItem("IT", appmsgBundle.getString("country.name.it")));
    availableCountries.add(new SelectItem("LV", appmsgBundle.getString("country.name.lv")));
    availableCountries.add(new SelectItem("LI", appmsgBundle.getString("country.name.li")));
    availableCountries.add(new SelectItem("LT", appmsgBundle.getString("country.name.lt")));
    availableCountries.add(new SelectItem("LU", appmsgBundle.getString("country.name.lu")));
    availableCountries.add(new SelectItem("MT", appmsgBundle.getString("country.name.mt")));
    availableCountries.add(new SelectItem("NL", appmsgBundle.getString("country.name.nl")));
    availableCountries.add(new SelectItem("NO", appmsgBundle.getString("country.name.no")));
    availableCountries.add(new SelectItem("PL", appmsgBundle.getString("country.name.pl")));
    availableCountries.add(new SelectItem("PT", appmsgBundle.getString("country.name.pt")));
    availableCountries.add(new SelectItem("RO", appmsgBundle.getString("country.name.ro")));
    availableCountries.add(new SelectItem("SK", appmsgBundle.getString("country.name.sk")));
    availableCountries.add(new SelectItem("SI", appmsgBundle.getString("country.name.si")));
    availableCountries.add(new SelectItem("ES", appmsgBundle.getString("country.name.es")));
    availableCountries.add(new SelectItem("SE", appmsgBundle.getString("country.name.se")));
    availableCountries.add(new SelectItem("CH", appmsgBundle.getString("country.name.ch")));
    availableCountries.add(new SelectItem("UK", appmsgBundle.getString("country.name.uk")));
    availableCountries.add(new SelectItem("AU", appmsgBundle.getString("country.name.au")));
    availableCountries.add(new SelectItem("BR", appmsgBundle.getString("country.name.br")));
    availableCountries.add(new SelectItem("CA", appmsgBundle.getString("country.name.ca")));
    availableCountries.add(new SelectItem("CN", appmsgBundle.getString("country.name.cn")));
    availableCountries.add(new SelectItem("HR", appmsgBundle.getString("country.name.hr")));
    availableCountries.add(new SelectItem("SR", appmsgBundle.getString("country.name.sr")));
    availableCountries.add(new SelectItem("IN", appmsgBundle.getString("country.name.in")));
    availableCountries.add(new SelectItem("MX", appmsgBundle.getString("country.name.mx")));
    availableCountries.add(new SelectItem("RU", appmsgBundle.getString("country.name.ru")));
    availableCountries.add(new SelectItem("ZA", appmsgBundle.getString("country.name.za")));
    availableCountries.add(new SelectItem("TR", appmsgBundle.getString("country.name.tr")));
    availableCountries.add(new SelectItem("US", appmsgBundle.getString("country.name.us")));
    availableCountries.add(new SelectItem("Other", appmsgBundle.getString("country.name.other")));
    availableCountries.add(new SelectItem("Do not know", appmsgBundle.getString("country.name.do.not.know")));

    // }

    return availableCountries;
  }

  public String getCountryShort(String countryName) {

    if ("Austria".equals(countryName))
      return "AT";

    if ("Belgium".equals(countryName))
      return "BE";

    if ("Bulgaria".equals(countryName))
      return "BG";

    if ("Cyprus".equals(countryName))
      return "CY";

    if ("Czech Republic".equals(countryName))
      return "CZ";

    if ("Denmark".equals(countryName))
      return "DK";

    if ("Estonia".equals(countryName))
      return "EE";

    if ("Finland".equals(countryName))
      return "FI";

    if ("France".equals(countryName))
      return "FR";

    if ("Germany".equals(countryName))
      return "DE";

    if ("Greece".equals(countryName))
      return "GR";

    if ("Hungary".equals(countryName))
      return "HU";

    if ("Iceland".equals(countryName))
      return "IS";

    if ("Ireland".equals(countryName))
      return "IE";

    if ("Italy".equals(countryName))
      return "IT";

    if ("Latvia".equals(countryName))
      return "LV";

    if ("Liechtenstein".equals(countryName))
      return "LI";

    if ("Lithuania".equals(countryName))
      return "LT";

    if ("Luxembourg".equals(countryName))
      return "LU";

    if ("Malta".equals(countryName))
      return "MT";

    if ("Netherlands".equals(countryName))
      return "NL";

    if ("Norway".equals(countryName))
      return "NO";

    if ("Poland".equals(countryName))
      return "PL";

    if ("Portugal".equals(countryName))
      return "PT";

    if ("Romania".equals(countryName))
      return "RO";

    if ("Slovakia".equals(countryName))
      return "SK";

    if ("Slovenia".equals(countryName))
      return "SI";

    if ("Spain".equals(countryName))
      return "ES";

    if ("Sweden".equals(countryName))
      return "SE";

    if ("Switzerland".equals(countryName))
      return "CH";

    if ("United Kingdom".equals(countryName))
      return "UK";

    return "Other";

  }

  public static char[] getALL_CHARS() {
    List<Character> chars = new ArrayList<Character>(40);

    if (ALL_CHARS == null) {
      for (char c = 'a'; c <= 'z'; ++c) {
        chars.add(c);
      }

      for (char c = 'A'; c <= 'Z'; ++c) {
        chars.add(c);
      }

      for (char c = '0'; c <= '9'; ++c) {
        chars.add(c);
      }

      ALL_CHARS = new char[chars.size()];
      for (int i = 0; i < chars.size(); i++) {
        ALL_CHARS[i] = chars.get(i);
      }
    }

    return ALL_CHARS;
  }

  public String generateUser(String country) {
    getALL_CHARS();

    StringBuilder sb = new StringBuilder(getCountryShort(country)).append('-');
    sb.append(ALL_CHARS[Math.abs(RANDOM.nextInt(ALL_CHARS.length))]);
    sb.append(ALL_CHARS[Math.abs(RANDOM.nextInt(ALL_CHARS.length))]);
    sb.append('-');
    sb.append(ALL_CHARS[Math.abs(RANDOM.nextInt(ALL_CHARS.length))]);
    sb.append(ALL_CHARS[Math.abs(RANDOM.nextInt(ALL_CHARS.length))]);
    sb.append(ALL_CHARS[Math.abs(RANDOM.nextInt(ALL_CHARS.length))]);

    return sb.toString();
  }

  public static final String getDigitsFromLogin(String login, int index) {
    StringBuilder sb = new StringBuilder();

    for (int i = index; i < index + 3; i++) {
      sb.append((int) login.charAt(i));
    }

    return sb.substring(0, 6);
  }

  public static final String getKey(String login, int index) {
    final String first = getDigitsFromLogin(login, index);
    final String last = getDigitsFromLogin(login, index + 2);

    return (calculate(first) + "" + last);
  }

  public static final int calculate(String l) {
    int sum = 0;
    for (int i = 0; i < l.length(); i++) {
      sum += Integer.parseInt("" + l.charAt(i));
    }

    int[] delta = new int[] { 0, 1, 2, 3, 4, -4, -3, -2, -1, 0 };

    for (int i = l.length() - 1; i > 0; i += -2) {
      int delta_index = Integer.parseInt(l.substring(i, i + 1));
      int delta_value = delta[delta_index];
      sum += delta_value;
    }

    int mod10 = 10 - (sum % 10);
    if (mod10 == 10)
      return 0;

    return mod10;
  }

  public List<String> getKeys(String login) {
    ArrayList<String> ret = new ArrayList<String>();

    for (int i = 0; i < login.length() - 4; i++) {
      ret.add(getKey(login, i));
    }

    return ret;
  }

  public static void main(String[] args) {
    System.out.println(new UtilsImpl().getKeys("EE-V1-z38"));
  }

  public String getVersion() {
    return appState.getVersion();
  }
}
