package com.nagarro.meetingbot.service;

public class BotService {/*
   
    @GET
    @Path(value="/readFile/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonWrapper getData(@PathParam("date") String date){
        List<DailyStatus> list = null;
        DailyStatus status = null;
        InputStream fileIs = null;
        ObjectInputStream objIs = null;
        JsonWrapper output = new JsonWrapper();
        List<JSonData> items = new LinkedList<JSonData>();
        try {
            fileIs = new FileInputStream(".\\dailystatus"+date+".ser");
            objIs = new ObjectInputStream(fileIs);
            list=(List<DailyStatus>)(objIs.readObject());
            
            for (DailyStatus dailyStatus : list) {
                JSonData json = new JSonData();
                json.setEmail(dailyStatus.getEmail());
                json.setMessage(dailyStatus.getData());
                items.add(json);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(objIs != null) objIs.close();
            } catch (Exception ex){
                 
            }
        }      

        output.setInputJsons(items);
        return output;
    }
    
    @POST
    @Path(value = "/savemailstofile")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveMessagestoFile(JsonWrapper inputJsonWrapper){
        BaseModel responseModel = new BaseModel();
        DateFormat df = new SimpleDateFormat("dd-MM-yy");
        Date dateobj = new Date();
        List<DailyStatus> list = new LinkedList<>();
        try{
                for(JSonData input :inputJsonWrapper.getInputJsons()){
                    String email = input.getEmail();
                        System.out.print("Testing");
                        String status = input.getMessage();
                        String meetingId = input.getMeetingId();
                        DailyStatus dailyStatus = new DailyStatus();
                        dailyStatus.setData(status);
                        dailyStatus.setEmail(email);
                        if(meetingId!=null)
                            dailyStatus.setMeetingId(Integer.parseInt(meetingId));
                        list.add(dailyStatus);
                        
                }
                FileOutputStream fout = new FileOutputStream(new java.io.File(".\\dailystatus"+df.format(dateobj)+".ser").getCanonicalFile(),true);
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(list);
                oos.close();
                responseModel.setHttpResponseCode(HttpStatus.SC_OK);
                responseModel.setError(HttpStatus.getStatusText(HttpStatus.SC_OK));
                
        }
        catch (IllegalArgumentException e) {
             responseModel = new BaseModel();
             responseModel.setHttpResponseCode(HttpStatus.SC_BAD_REQUEST);
             responseModel.setError(HttpStatus.getStatusText(HttpStatus.SC_BAD_REQUEST) + " "+e.getMessage());
        }catch(Exception e){
            responseModel = new BaseModel();
            responseModel.setHttpResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            responseModel.setError(HttpStatus.getStatusText(HttpStatus.SC_INTERNAL_SERVER_ERROR) +" "+e.getMessage());
        }
         return Response.status(responseModel.getHttpResponseCode()).entity(responseModel.getError()).build();
    }
    
    @GET
    @Path(value="/readStatus/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonWrapper getDailyStatus(@PathParam("date") String date){
        JsonWrapper output = new JsonWrapper();
        try{
        DailyStatusDAO dailyStatusDAO= new DailyStatusDAOImpl();
        List<DailyStatus> list = null;
        List<JSonData> items = new LinkedList<JSonData>();
        list = dailyStatusDAO.readDailyStatus(date);
            
            for (DailyStatus dailyStatus : list) {
                JSonData json = new JSonData();
                json.setEmail(dailyStatus.getEmail());
                json.setMessage(dailyStatus.getData());
                items.add(json);
            }
        output.setInputJsons(items);
        
        }catch(Exception ex){
            
        }
        return output;
    }

    @POST
    @Path(value = "/savetodb")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveMessagestoDB(JsonWrapper inputJsonWrapper){
        BaseModel responseModel = new BaseModel();
        List<DailyStatus> list = new LinkedList<>();
        DailyStatusDAO dailyStatusDAO= new DailyStatusDAOImpl();
        try{
                for(JSonData input :inputJsonWrapper.getInputJsons()){
                    String email = input.getEmail();
                        String status = input.getMessage();
                        String meetingId = input.getMeetingId();
                        DailyStatus dailyStatus = new DailyStatus();
                        dailyStatus.setData(status);
                        dailyStatus.setEmail(email);
                        if(meetingId!=null)
                            dailyStatus.setMeetingId(Integer.parseInt(meetingId));
                        list.add(dailyStatus);
                }
                dailyStatusDAO.saveDailyStatus(list);
                responseModel.setHttpResponseCode(HttpStatus.SC_OK);
                responseModel.setError(HttpStatus.getStatusText(HttpStatus.SC_OK));
                
        }
        catch (IllegalArgumentException e) {
             responseModel = new BaseModel();
             responseModel.setHttpResponseCode(HttpStatus.SC_BAD_REQUEST);
             responseModel.setError(HttpStatus.getStatusText(HttpStatus.SC_BAD_REQUEST) + " "+e.getMessage());
        }catch(Exception e){
            responseModel = new BaseModel();
            responseModel.setHttpResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            responseModel.setError(HttpStatus.getStatusText(HttpStatus.SC_INTERNAL_SERVER_ERROR) +" "+e.getMessage());
        }
         return Response.status(responseModel.getHttpResponseCode()).entity(responseModel.getError()).build();
    }

*/}
