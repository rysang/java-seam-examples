package eu.cec.sanco.utils.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;

import eu.cec.sanco.beans.AppState;
import eu.cec.sanco.beans.Organisation;
import eu.cec.sanco.utils.api.Utils;

@Scope("singleton")
@Component("utils")
public class UtilsImpl implements Utils {
  private static final transient Logger LOG = Logger.getLogger(UtilsImpl.class);

  @Autowired
  private AppState appState;

  @Autowired
  private Organisation organisation;

  private String theme = "mint-choc";

  private static char[] ALL_CHARS = null;

  private final Random RANDOM = new Random(System.currentTimeMillis());

  static {
    getALL_CHARS();
  }

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

  @TriggersRemove(cacheName = "eu.cec.sanco.eccrs.persistence", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
  public void reset() {
    LOG.info("Resetting caches.");
  }

  @TriggersRemove(cacheName = "eu.cec.sanco.eccrs.selects", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
  public void resetSelects() {
    LOG.info("Resetting select caches.");
  }

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.selects")
  public List<SelectItem> getYears() {
    ArrayList<SelectItem> years = new ArrayList<SelectItem>(20);
    int currentYear = new Date().getYear() + 1900;

    for (int i = 2010; i <= currentYear; i++) {
      years.add(new SelectItem(i, String.valueOf(i)));
    }

    return years;
  }

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.selects")
  public List<SelectItem> getPaymentMeans() {
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle appmsgBundle = context.getApplication().getResourceBundle(context, "appmsg");

    ArrayList<SelectItem> paymentMeans = new ArrayList<SelectItem>(20);
    paymentMeans.add(new SelectItem("Cash", appmsgBundle.getString("payment.means.cash")));
    paymentMeans.add(new SelectItem("Debit card", appmsgBundle.getString("payment.means.debit.card")));
    paymentMeans.add(new SelectItem("Credit card", appmsgBundle.getString("payment.means.credit.card")));
    paymentMeans.add(new SelectItem("Paper cheque, paper-based vouchers and paper-based traveller's cheques",
        appmsgBundle.getString("payment.means.paper.cheque.paper.based.vouchers.and.paper.based.traveller.s.cheques")));
    paymentMeans.add(new SelectItem("Credit transfer", appmsgBundle.getString("payment.means.credit.transfer")));
    paymentMeans.add(new SelectItem("Direct debit", appmsgBundle.getString("payment.means.direct.debit")));
    paymentMeans.add(new SelectItem("Electronic money", appmsgBundle.getString("payment.means.electronic.money")));
    paymentMeans.add(new SelectItem("Money remittance", appmsgBundle.getString("payment.means.money.remittance")));
    paymentMeans.add(new SelectItem("Pre-paid cards", appmsgBundle.getString("payment.means.pre.paid.cards")));
    paymentMeans.add(new SelectItem("Mobile payments (e.g. SMS)", appmsgBundle
        .getString("payment.means.mobile.payments.e.g.sms")));
    paymentMeans.add(new SelectItem("Other", appmsgBundle.getString("payment.means.other")));
    paymentMeans.add(new SelectItem("Do not know", appmsgBundle.getString("payment.means.do.not.know")));

    return paymentMeans;
  }

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.selects")
  public List<SelectItem> getCurrencies() {
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle appmsgBundle = context.getApplication().getResourceBundle(context, "appmsg");

    ArrayList<SelectItem> currencies = new ArrayList<SelectItem>(20);
    currencies.add(new SelectItem("EUR", appmsgBundle.getString("transaction.currency.eur")));
    currencies.add(new SelectItem("BRL", appmsgBundle.getString("transaction.currency.brl")));
    currencies.add(new SelectItem("CZK", appmsgBundle.getString("transaction.currency.czk")));
    currencies.add(new SelectItem("DKK", appmsgBundle.getString("transaction.currency.dkk")));
    currencies.add(new SelectItem("EEK", appmsgBundle.getString("transaction.currency.eek")));
    currencies.add(new SelectItem("HUF", appmsgBundle.getString("transaction.currency.huf")));
    currencies.add(new SelectItem("LVL", appmsgBundle.getString("transaction.currency.lvl")));
    currencies.add(new SelectItem("LTL", appmsgBundle.getString("transaction.currency.ltl")));
    currencies.add(new SelectItem("PLN", appmsgBundle.getString("transaction.currency.pln")));
    currencies.add(new SelectItem("RON", appmsgBundle.getString("transaction.currency.ron")));
    currencies.add(new SelectItem("SEK", appmsgBundle.getString("transaction.currency.sek")));
    currencies.add(new SelectItem("GBP", appmsgBundle.getString("transaction.currency.gbp")));
    currencies.add(new SelectItem("ISK", appmsgBundle.getString("transaction.currency.isk")));
    currencies.add(new SelectItem("CHF", appmsgBundle.getString("transaction.currency.chf")));
    currencies.add(new SelectItem("NOK", appmsgBundle.getString("transaction.currency.nok")));
    currencies.add(new SelectItem("USD", appmsgBundle.getString("transaction.currency.usd")));
    currencies.add(new SelectItem("Other", appmsgBundle.getString("transaction.currency.other")));

    return currencies;
  }

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.selects")
  public List<SelectItem> getLevel1Classifications() {
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle appmsgBundle = context.getApplication().getResourceBundle(context, "appmsg");

    ArrayList<SelectItem> level1 = new ArrayList<SelectItem>(20);
    level1.add(new SelectItem("Quality of goods and services", appmsgBundle
        .getString("levels.quality.of.goods.and.services.value")));
    level1.add(new SelectItem("Delivery of goods/ Provision of services", appmsgBundle
        .getString("levels.delivery.of.goods.provision.of.services.value")));
    level1.add(new SelectItem("Price / Tariff", appmsgBundle.getString("levels.price.tariff.value")));
    level1.add(new SelectItem("Invoicing / billing and debt collection", appmsgBundle
        .getString("levels.invoicing.billing.and.debt.collection.value")));
    level1.add(new SelectItem("Warranty / statutory guarantee and commercial guarantees", appmsgBundle
        .getString("levels.warranty.statutory.guarantee.and.commercial.guarantees.value")));
    level1.add(new SelectItem("Redress", appmsgBundle.getString("levels.redress.value")));
    level1.add(new SelectItem("Unfair Commercial Practices", appmsgBundle
        .getString("levels.unfair.commercial.practices.value")));
    level1.add(new SelectItem("Contracts and sales", appmsgBundle.getString("levels.contracts.and.sales.value")));
    level1.add(new SelectItem("Provider change / switching", appmsgBundle
        .getString("levels.provider.change.switching.value")));
    level1.add(new SelectItem("Safety - covers both goods (including food) and services", appmsgBundle
        .getString("levels.safety.covers.both.goods.including.food.and.services.value")));
    level1.add(new SelectItem("Privacy and data protection", appmsgBundle
        .getString("levels.privacy.and.data.protection.value")));
    level1.add(new SelectItem("Other issues", appmsgBundle.getString("levels.other.issues.value")));

    return level1;
  }

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.selects")
  public List<SelectItem> getLevel2Classifications(String level1) {

    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle appmsgBundle = context.getApplication().getResourceBundle(context, "appmsg");

    ArrayList<SelectItem> level2 = new ArrayList<SelectItem>(20);

    if (StringUtils.isEmpty(level1) || level1.equals("Quality of goods and services")) {
      level2.add(new SelectItem("Defective, caused damage", appmsgBundle
          .getString("levels.quality.of.goods.and.services.defective.caused.damage")));
      level2.add(new SelectItem("Not in conformity with order", appmsgBundle
          .getString("levels.quality.of.goods.and.services.not.in.conformity.with.order")));
      level2.add(new SelectItem("Not fit for particular purpose", appmsgBundle
          .getString("levels.quality.of.goods.and.services.not.fit.for.particular.purpose")));
      level2.add(new SelectItem("Other issues", appmsgBundle
          .getString("levels.quality.of.goods.and.services.other.issues")));
    }

    else if (level1.equals("Delivery of goods/ Provision of services")) {
      level2.add(new SelectItem("Not delivered / not provided", appmsgBundle
          .getString("levels.delivery.of.goods.provision.of.services.not.delivered.not.provided")));
      level2.add(new SelectItem("Partially delivered / partially provided", appmsgBundle
          .getString("levels.delivery.of.goods.provision.of.services.partially.delivered.partially.provided")));
      level2
          .add(new SelectItem("Delay", appmsgBundle.getString("levels.delivery.of.goods.provision.of.services.delay")));
      level2.add(new SelectItem("Not available / No access", appmsgBundle
          .getString("levels.delivery.of.goods.provision.of.services.not.available.no.access")));
      level2.add(new SelectItem("Refusal to sell / provide a good or a service", appmsgBundle
          .getString("levels.delivery.of.goods.provision.of.services.refusal.to.sell.provide.a.good.or.a.service")));
      level2
          .add(new SelectItem(
              "Suspension of the delivery of a good or the provision of a service without prior notice",
              appmsgBundle
                  .getString("levels.delivery.of.goods.provision.of.services.suspension.of.the.delivery.of.a.good.or.the.provision.of.a.service.without.prior.notice")));
      level2.add(new SelectItem("Opening hours", appmsgBundle
          .getString("levels.delivery.of.goods.provision.of.services.opening.hours")));
      level2.add(new SelectItem("Customer service", appmsgBundle
          .getString("levels.delivery.of.goods.provision.of.services.customer.service")));
      level2.add(new SelectItem("After-sales service/assistance", appmsgBundle
          .getString("levels.delivery.of.goods.provision.of.services.after.sales.service.assistance")));
      level2
          .add(new SelectItem(
              "Other issues related to the delivery of goods/provisions of services",
              appmsgBundle
                  .getString("levels.delivery.of.goods.provision.of.services.other.issues.related.to.the.delivery.of.goods.provisions.of.services")));

    }

    else if (level1.equals("Price / Tariff")) {

      level2.add(new SelectItem("Price / tariff change", appmsgBundle
          .getString("levels.price.tariff.price.tariff.change")));
      level2.add(new SelectItem("Price discrimination", appmsgBundle
          .getString("levels.price.tariff.price.discrimination")));
      level2.add(new SelectItem("Tariff transparency (unclear, complex)", appmsgBundle
          .getString("levels.price.tariff.tariff.transparency.unclear.complex")));
      level2.add(new SelectItem("Other issues related to price/tariff", appmsgBundle
          .getString("levels.price.tariff.other.issues.related.to.price.tariff")));
    }

    else if (level1.equals("Invoicing / billing and debt collection")) {
      level2.add(new SelectItem("Incorrect invoice / bill", appmsgBundle
          .getString("levels.invoicing.billing.and.debt.collection.incorrect.invoice.bill")));
      level2.add(new SelectItem("Unclear invoice / bill", appmsgBundle
          .getString("levels.invoicing.billing.and.debt.collection.unclear.invoice.bill")));
      level2
          .add(new SelectItem(
              "Non-issue of invoice or difficult access to invoice/monthly statement",
              appmsgBundle
                  .getString("levels.invoicing.billing.and.debt.collection.non.issue.of.invoice.or.difficult.access.to.invoice.monthly.statement")));
      level2.add(new SelectItem("Unjustified invoicing / billing", appmsgBundle
          .getString("levels.invoicing.billing.and.debt.collection.unjustified.invoicing.billing")));
      level2.add(new SelectItem("Debt collection", appmsgBundle
          .getString("levels.invoicing.billing.and.debt.collection.debt.collection")));
      level2
          .add(new SelectItem(
              "Other issues related to invoicing/billing and debt collection",
              appmsgBundle
                  .getString("levels.invoicing.billing.and.debt.collection.other.issues.related.to.invoicing.billing.and.debt.collection")));
    }

    else if (level1.equals("Warranty / statutory guarantee and commercial guarantees")) {

      level2
          .add(new SelectItem(
              "Warranty / statutory guarantee not honoured",
              appmsgBundle
                  .getString("levels.warranty.statutory.guarantee.and.commercial.guarantees.warranty.statutory.guarantee.not.honoured")));
      level2
          .add(new SelectItem(
              "Commercial guarantee not honoured",
              appmsgBundle
                  .getString("levels.warranty.statutory.guarantee.and.commercial.guarantees.commercial.guarantee.not.honoured")));
      level2.add(new SelectItem("Other issues", appmsgBundle
          .getString("levels.warranty.statutory.guarantee.and.commercial.guarantees.other.issues")));

    }

    else if (level1.equals("Redress")) {
      level2.add(new SelectItem("Difficult access to redress", appmsgBundle
          .getString("levels.redress.difficult.access.to.redress")));
      level2.add(new SelectItem("No redress", appmsgBundle.getString("levels.redress.no.redress")));
      level2.add(new SelectItem("Part or incorrect redress", appmsgBundle
          .getString("levels.redress.part.or.incorrect.redress")));
      level2.add(new SelectItem("Delayed redress", appmsgBundle.getString("levels.redress.delayed.redress")));
      level2.add(new SelectItem("Other issues related to redress", appmsgBundle
          .getString("levels.redress.other.issues.related.to.redress")));

    }

    else if (level1.equals("Unfair Commercial Practices")) {

      level2.add(new SelectItem("Misleading contractual terms and conditions", appmsgBundle
          .getString("levels.unfair.commercial.practices.misleading.contractual.terms.and.conditions")));
      level2
          .add(new SelectItem(
              "Incorrect or misleading indication of prices / tariffs and labelling",
              appmsgBundle
                  .getString("levels.unfair.commercial.practices.incorrect.or.misleading.indication.of.prices.tariffs.and.labelling")));
      level2.add(new SelectItem("Misleading advertising", appmsgBundle
          .getString("levels.unfair.commercial.practices.misleading.advertising")));
      level2.add(new SelectItem("Unsolicited advertising", appmsgBundle
          .getString("levels.unfair.commercial.practices.unsolicited.advertising")));
      level2.add(new SelectItem("Unsolicited goods or services", appmsgBundle
          .getString("levels.unfair.commercial.practices.unsolicited.goods.or.services")));
      level2.add(new SelectItem("Aggressive selling practices", appmsgBundle
          .getString("levels.unfair.commercial.practices.aggressive.selling.practices")));
      level2.add(new SelectItem("Fraudulent practices", appmsgBundle
          .getString("levels.unfair.commercial.practices.fraudulent.practices")));
      level2.add(new SelectItem("Other unfair commercial practices", appmsgBundle
          .getString("levels.unfair.commercial.practices.other.unfair.commercial.practices")));

    }

    else if (level1.equals("Contracts and sales")) {

      level2.add(new SelectItem("Unfair contractual terms / change of contractual terms", appmsgBundle
          .getString("levels.contracts.and.sales.unfair.contractual.terms.change.of.contractual.terms")));
      level2.add(new SelectItem("Lack of information", appmsgBundle
          .getString("levels.contracts.and.sales.lack.of.information")));
      level2.add(new SelectItem("Order confirmation (not received/wrong)", appmsgBundle
          .getString("levels.contracts.and.sales.order.confirmation.not.received.wrong")));
      level2.add(new SelectItem("Cooling-off period / Right of withdrawal", appmsgBundle
          .getString("levels.contracts.and.sales.cooling.off.period.right.of.withdrawal")));
      level2.add(new SelectItem("Payments (e.g. prepayments and instalments)", appmsgBundle
          .getString("levels.contracts.and.sales.payments.e.g.prepayments.and.instalments")));
      level2.add(new SelectItem("Rescission of contract", appmsgBundle
          .getString("levels.contracts.and.sales.rescission.of.contract")));
      level2.add(new SelectItem("Minimum contractual period", appmsgBundle
          .getString("levels.contracts.and.sales.minimum.contractual.period")));
      level2.add(new SelectItem("Other issues related to contracts and sales", appmsgBundle
          .getString("levels.contracts.and.sales.other.issues.related.to.contracts.and.sales")));

    }

    else if (level1.equals("Provider change / switching")) {
      level2.add(new SelectItem("Provider change / switching", appmsgBundle
          .getString("levels.provider.change.switching.provider.change.switching")));
      level2
          .add(new SelectItem("Other issues", appmsgBundle.getString("levels.provider.change.switching.other.issues")));
    }

    else if (level1.equals("Safety - covers both goods (including food) and services")) {

      level2
          .add(new SelectItem(
              "Product safety - covers both goods (including food) and services",
              appmsgBundle
                  .getString("levels.safety.covers.both.goods.including.food.and.services.product.safety.covers.both.goods.including.food.and.services")));
      level2
          .add(new SelectItem(
              "Package, labelling and instructions - covers both goods (including food) and services",
              appmsgBundle
                  .getString("levels.safety.covers.both.goods.including.food.and.services.package.labelling.and.instructions.covers.both.goods.including.food.and.services")));
      level2.add(new SelectItem("Other issues", appmsgBundle
          .getString("levels.safety.covers.both.goods.including.food.and.services.other.issues")));

    }

    else if (level1.equals("Privacy and data protection")) {

      level2.add(new SelectItem("Data protection", appmsgBundle
          .getString("levels.privacy.and.data.protection.data.protection")));
      level2.add(new SelectItem("Privacy", appmsgBundle.getString("levels.privacy.and.data.protection.privacy")));
      level2.add(new SelectItem("Other issues related to privacy and data protection", appmsgBundle
          .getString("levels.privacy.and.data.protection.other.issues.related.to.privacy.and.data.protection")));

    }

    else if (level1.equals("Other issues")) {

      level2.add(new SelectItem("Other issues", appmsgBundle.getString("levels.other.issues.other.issues")));

    }

    return level2;
  }

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.selects")
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

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.selects")
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

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.selects")
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

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.selects")
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

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.selects")
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

  public String getCountry() {
    return organisation.getCountry();
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public String getTheme() {
    return theme;
  }
}
