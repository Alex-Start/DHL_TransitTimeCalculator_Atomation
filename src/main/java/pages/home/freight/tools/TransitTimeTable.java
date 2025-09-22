package pages.home.freight.tools;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransitTimeTable {
    private final List<String> features = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();

    public void addFeatures(List<String> features) {
        this.features.addAll(features);
    }

    public List<String> getFeatures() {
        return features;
    }

    public ProductBuilder addProduct() {
        return new ProductBuilder();
    }

    public class ProductBuilder {
        private String option;
        private String name;
        private String description;
        private String deliveryDate;
        private Map<String, String> featureStates = new LinkedHashMap<>();

        private ProductBuilder() {}

        public ProductBuilder addFeatureState(String feature, String state) {
            featureStates.put(feature, state);
            return this;
        }

        public ProductBuilder addOption(String option) {
            this.option = option;
            return this;
        }

        public ProductBuilder addProductName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder addDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder addDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;
        }

        public Product build() {
            Product product = new Product(option, name, description, deliveryDate, featureStates);
            products.add(product);
            return product;
        }
    }

    public static class Product {
        private final String name;
        private final String option;
        private final String description;
        private final String deliveryDate;
        private final Map<String, String> featureStates;

        public Product(String option, String name, String description, String deliveryDate, Map<String, String> featureStates) {
            this.option = option;
            this.name = name;
            this.description = description;
            this.deliveryDate = deliveryDate;
            this.featureStates = new LinkedHashMap<>(featureStates);
        }

        public String getOption() { return option; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getDeliveryDate() { return deliveryDate; }
        public Map<String, String> getFeatureStates() { return new LinkedHashMap<>(featureStates); }

        @Override
        public String toString() {
            return "Product{" +
                    "name='" + name + '\'' +
                    ", option='" + option + '\'' +
                    ", description='" + description + '\'' +
                    ", deliveryDate='" + deliveryDate + '\'' +
                    ", featureStates=" + featureStates +
                    '}';
        }
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }
}
