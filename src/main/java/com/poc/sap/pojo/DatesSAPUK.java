
package com.poc.sap.pojo;

import javax.annotation.Generated;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@CsvRecord(separator = ",")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "impressionNumber",
    "publicationDate",
    "estimatedStockInDate",
    "productISBN"
})
public class DatesSAPUK {

    /**
     * ImpressionNumber
     * <p>
     * 
     * (Required)
     * 
     */
	@DataField(pos=4,paddingChar='0' , length=2 , align = "L",pattern = "00")
    @JsonProperty("impressionNumber")
    private String impressionNumber = "";
    /**
     * PublicationDate
     * <p>
     * 
     * 
     */
	@DataField(pos=2)
    @JsonProperty("publicationDate")
    private String publicationDate;
    /**
     * EstimatedStockInDate
     * <p>
     * 
     * 
     */
	@DataField(pos=3)
    @JsonProperty("estimatedStockInDate")
    private String estimatedStockInDate = "";
    /**
     * Product ISBN
     * <p>
     * 
     * (Required)
     * 
     */
	@DataField(pos=1)
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
