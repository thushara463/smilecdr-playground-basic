import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import com.google.common.util.concurrent.AtomicDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class IClientInterceptorImpl implements IClientInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(IClientInterceptorImpl.class);

    private final AtomicInteger totalRequestCount = new AtomicInteger();
    private final AtomicDouble totalResponseTime = new AtomicDouble();


    @Override
    public void interceptRequest(IHttpRequest iHttpRequest) {

        totalRequestCount.incrementAndGet();
        //LOGGER.info("Client Request : {}", iHttpRequest);
    }

    @Override
    public void interceptResponse(IHttpResponse iHttpResponse)   {

        totalResponseTime.addAndGet(iHttpResponse.getRequestStopWatch().getMillis());
        //LOGGER.info("Time taken for the request is :{}", iHttpResponse.getRequestStopWatch().toString());
    }

    public double getAverageResponseTime(){
        return totalRequestCount.get() != 0 ? (totalResponseTime.get()/totalRequestCount.get()) : 0;
    }

}
