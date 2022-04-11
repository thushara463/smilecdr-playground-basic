import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.CacheControlDirective;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class SampleClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleClient.class);
    private static final String FILE_NAME = "lastnames.txt";

    public static void main(String[] theArgs) {

        // Create a FHIR client
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
        //client.registerInterceptor(new LoggingInterceptor(false));

        FileReader reader = new FileReader();
        List<String> lastNames;
        try {
            lastNames = reader.loadLastNames(FILE_NAME);
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("Exception occurred while reading file " + FILE_NAME, e);
            return;
        }

        SampleClient sampleClient = new SampleClient();
        sampleClient.searchSurnameList(client, lastNames, false);
        sampleClient.searchSurnameList(client, lastNames, false);
        sampleClient.searchSurnameList(client, lastNames, true);

    }

    protected double searchSurnameList(IGenericClient client, List<String> lastnames, boolean isCacheDisabled){
        IClientInterceptorImpl iClientInterceptor = new IClientInterceptorImpl();
        client.registerInterceptor(iClientInterceptor);

        lastnames.forEach(surname -> {
            // Search for Patient resources
            Bundle response = client
                    .search()
                    .forResource("Patient")
                    .where(Patient.FAMILY.matches().value(surname))
                    .returnBundle(Bundle.class)
                    .cacheControl(getCacheControlDirective(isCacheDisabled))
                    .execute();
            //LOGGER.info("Response received for surname:{}", surname);

        });

        double averageTime1 = iClientInterceptor.getAverageResponseTime();
        LOGGER.info("Average time taken {}", averageTime1);

        return averageTime1;
    }

    protected CacheControlDirective getCacheControlDirective(boolean isCacheDisabled){
        CacheControlDirective cacheControlDirective = new CacheControlDirective();
        cacheControlDirective.setNoCache(isCacheDisabled);
        return cacheControlDirective;
    }

}
