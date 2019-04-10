
package com.poc.cdm.pojo;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "impressionNumber",
    "publicationDate",
    "estimatedStockInDate",
    "productISBN"
})
public class DatesCDMUK {

    /**
     * ImpressionNumber
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("impressionNumber")
    private String impressionNumber = "";
    /**
     * PublicationDate
     * <p>
     * 
     * 
     */
    @JsonProperty("publicationDate")
    private String publicationDate;
    /**
     * EstimatedStockInDate
     * <p>
     * 
     * 
     */
    @JsonProperty("estimatedStockInDate")
    private String estimatedStockInDate = "";
    /**
     * Product ISBN
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("productISBN")
    private String productISBN = "";

    /**
     * ImpressionNumber
     * <p>
     * 
     * (Required)
     * 
     * @return
     *     The impressionNumber
     */
    @JsonProperty("impressionNumber")
    public String getImpressionNumber() {
        return impressionNumber;
    }

    /**
     * ImpressionNumber
     * <p>
     * 
     * (Required)
     * 
     * @param impressionNumber
     *     The impressionNumber
     */
    @JsonProperty("impressionNumber")
    public void setImpressionNumber(String impressionNumber) {
        this.impressionNumber = impressionNumber;
    }

    /**
     * PublicationDate
     * <p>
     * 
     * 
     * @return
     *     The publicationDate
     */
    @JsonProperty("publicationDate")
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * PublicationDate
     * <p>
     * 
     * 
     * @param publicationDate
     *     The publicationDate
     */
    @JsonProperty("publicationDate")
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * EstimatedStockInDate
     * <p>
     * 
     * 
     * @return
     *     The estimatedStockInDate
     */
    @JsonProperty("estimatedStockInDate")
    public String getEstimatedStockInDate() {
        return estimatedStockInDate;
    }

    /**
     * EstimatedStockInDate
     * <p>
     * 
     * 
     * @param estimatedStockInDate
     *     The estimatedStockInDate
     */
    @JsonProperty("estimatedStockInDate")
    public void setEstimatedStockInDate(String estimatedStockInDate) {
        this.estimatedStockInDate = estimatedStockInDate;
    }

    /**
     * Product ISBN
     * <p>
     * 
     * (Required)
     * 
     * @return
     *     The productISBN
     */
    @JsonProperty("productISBN")
    public String getProductISBN() {
        return productISBN;
    }

    /**
     * Product ISBN
     * <p>
     * 
     * (Required)
     * 
     * @param productISBN
     *     The productISBN
     */
    @JsonProperty("productISBN")
    public void setProductISBN(String productISBN) {
        this.productISBN = productISBN;
    }

}
