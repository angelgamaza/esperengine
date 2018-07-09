import classes.EsperApi;

import java.util.LinkedHashMap;
import java.util.Map;

public class EsperMain {

    public static void main(String[] args){

        //Create Event Types
        Map<String, Object> eventTypeStructure = new LinkedHashMap<>();
        eventTypeStructure.put("value", Integer.class);
        eventTypeStructure.put("name", String.class);

        //Print Event Types List
        System.out.println("Event Types in Esper (Pre Event Types insertion): " + EsperApi.getAllEventTypesList());

        //Add Event Types to Esper Engine
        EsperApi.addEventType("EventType1", eventTypeStructure);
        EsperApi.addEventType("EventType2", eventTypeStructure);
        EsperApi.addEventType("EventType50", eventTypeStructure);

        //Print Event Types List
        System.out.println("Event Types in Esper (Pre EPL patterns insertion): " + EsperApi.getAllEventTypesList());

        //Print EPL Event Patterns List
        System.out.println("EPL Event Patterns in Esper: " + EsperApi.getAllEplEventPatternsList());

        //Add EPL Event Patterns
        EsperApi.addEplEventPattern("@Name(\"Pattern1\")\n" + "insert into Patterns\n" + "select e.value as value, e.name as name \n" + "from pattern [every e = EventType1(value <= 1500)]");
        EsperApi.addEplEventPattern("@Name(\"Pattern2\")\n" + "insert into Patterns\n" + "select e.value as value, e.name as name \n" + "from pattern [every e = EventType1(value <= 34)]");
        EsperApi.addEplEventPattern("@Name(\"Pattern3\")\n" + "insert into EventType1\n" + "select e.value as value, e.name as name \n" + "from pattern [every e = EventType1(value <= 2)]");

        //Print Event Types List
        System.out.println("Event Types in Esper (Post EPL patterns insertion): " + EsperApi.getAllEventTypesList());

        //Print EPL Event Patterns List
        System.out.println("EPL Event Patterns in Esper (Post EPL patterns insertion): " + EsperApi.getAllEplEventPatternsList());

        //Delete EPL Event Pattern
        EsperApi.deleteEplEventPattern("Pattern1");

        //Print EPL Event Patterns List
        System.out.println("EPL Event Patterns in Esper (Post EPL Event Pattern deletion): " + EsperApi.getAllEplEventPatternsList());

        //Event Type exists
        System.out.println("Event Type EventType50 exists in Esper: " + EsperApi.eventTypeExists("EventType50"));
        EsperApi.deleteEventType("EventType50");
        System.out.println("Event Types in Esper: " + EsperApi.getAllEventTypesList());
        System.out.println("Event Type EventType50 exists in Esper: " + EsperApi.eventTypeExists("EventType50"));

        //Get EPL Event Pattern Content
        System.out.println("Content of the EPL Event Pattern Pattern3:");
        System.out.println(EsperApi.getEplEventPatternContent("Pattern3"));

    }

}
