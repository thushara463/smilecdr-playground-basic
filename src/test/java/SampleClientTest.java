import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.CacheControlDirective;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class SampleClientTest {

    FhirContext fhirContext = FhirContext.forR4();
    IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
    SampleClient sampleClient = new SampleClient();

    @Test
    public void searchSurnameList() throws URISyntaxException, IOException {

        String filename = "lastnames.txt";
        FileReader fileReader = new FileReader();
        List<String> lastnames = fileReader.loadLastNames(filename);

        double loop1Time = sampleClient.searchSurnameList(client, lastnames, false);
        double loop2Time = sampleClient.searchSurnameList(client, lastnames, false);
        double loop3Time = sampleClient.searchSurnameList(client, lastnames, true);

        Assert.assertTrue(loop1Time > 0);
        Assert.assertTrue(loop1Time > loop2Time); //this can be false in case the cache have been loaded before the first search. Ex: rerunning the test cases
        Assert.assertTrue(loop3Time > loop2Time);
    }

    @Test
    public void testGetCacheControlDirectiveNoCache() {
        CacheControlDirective cacheControlDirective = sampleClient.getCacheControlDirective(true);
        Assert.assertTrue(cacheControlDirective.isNoCache());
    }

    @Test
    public void testGetCacheControlDirectiveWithCache() {
        CacheControlDirective cacheControlDirective = sampleClient.getCacheControlDirective(false);
        Assert.assertFalse(cacheControlDirective.isNoCache());
    }
}