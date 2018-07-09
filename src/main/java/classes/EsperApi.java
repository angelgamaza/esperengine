package classes;

import com.espertech.esper.client.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author Angel Manuel Gamaza Dominguez
 *
 * Esper main methods API
 *
 */

public class EsperApi {

    /** Service provider **/
    private static EPServiceProvider epService;

    /**
     * Get Global Esper Service
     */

    private static EPServiceProvider getService() {
        synchronized(EsperApi.class) {
            if (epService == null)
                epService = EPServiceProviderManager.getDefaultProvider(new Configuration());
        }
        return epService;
    }

    /**
     * Patterns
     */

    public static EPStatement addEplEventPattern(String eplContent) {
        return getService().getEPAdministrator().createEPL(eplContent);
    }

    public static String[] getAllEplEventPatterns() {
        return getService().getEPAdministrator().getStatementNames();
    }

    public static String getEplEventPatternContent(String eplEventPatternName){
        return getService().getEPAdministrator().getStatement(eplEventPatternName).getText();
    }

    public static void addListener(StatementAwareUpdateListener listener, EPStatement eventPattern) {
        eventPattern.addListener(listener);
    }

    public static boolean eplEventPatternExists(String eplEventPatternName){
        try{
            return getService().getEPAdministrator().getStatement(eplEventPatternName).isDestroyed();
        }catch (Exception e){
            return false;
        }
    }

    public static void deleteEplEventPattern(String eplEventPatternName){
        getService().getEPAdministrator().getStatement(eplEventPatternName).destroy();
    }

    /**
     * Event Types
     */

    public static void addEventType(String eventTypeName, Map<String, Object> eventTypeMap) {
        getService().getEPAdministrator().getConfiguration().addEventType(eventTypeName, eventTypeMap);
    }

    public static EventType[] getAllEventTypes(){
        return getService().getEPAdministrator().getConfiguration().getEventTypes();
    }

    public static boolean eventTypeExists(String eventTypeName) {
        return getService().getEPAdministrator().getConfiguration().isEventTypeExists(eventTypeName);
    }

    public static void deleteEventType(String eventTypeName) {
        getService().getEPAdministrator().getConfiguration().removeEventType(eventTypeName, true);
    }

    public static void sendEventType(Map<String,Object> eventMap, String eventTypeName) {
        getService().getEPRuntime().sendEvent(eventMap, eventTypeName);
    }

    /**
     * Convert to list methods
     */

    public static List<String> getAllEplEventPatternsList(){
        return Arrays.asList(getAllEplEventPatterns());
    }

    public static List<String> getAllEventTypesList(){
        List<String> eventTypesList = new ArrayList<>();
        EventType[] eventTypes = getAllEventTypes();
        for(EventType currentEventType : eventTypes)
            eventTypesList.add(currentEventType.getName());
        return eventTypesList;
    }

}
