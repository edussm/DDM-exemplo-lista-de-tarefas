package br.fpu.exemplolistactivity.network;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.comparator.BooleanComparator;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import br.fpu.exemplolistactivity.domain.Tarefa;

public class RestServiceClient extends Observable {
    private String serviceRootUrl;
    private static final String TAG = "rest-service";

    public RestServiceClient(String serviceRootUrl) {
        super();
        this.serviceRootUrl = serviceRootUrl;
    }

    @NonNull
    private HttpAuthentication getHttpAuthentication() {
        // Populate the HTTP Basic Authentitcation header with the username and password
        return new HttpBasicAuthentication("user", "password");
    }

    public void getTasks(Observer observer) {
        addObserver(observer);

        AsyncTask<Void, Void, List<Tarefa>> restCallTask =
                new AsyncTask<Void, Void, List<Tarefa>>() {
            @Override
           protected List<Tarefa> doInBackground(Void... params) {
                final String url = serviceRootUrl ;

                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(getHttpAuthentication());
                requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                try {
                    // Make the network request
                    Log.d(TAG, url);
                    ResponseEntity<List<Tarefa>> response;
                    response = restTemplate.exchange(url, HttpMethod.GET,
                            new HttpEntity<Object>(requestHeaders),
                            new ParameterizedTypeReference<List<Tarefa>>(){});


                    return response.getBody();
                } catch (HttpClientErrorException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                    return null;
                } catch (ResourceAccessException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Tarefa> tarefas) {
                Log.d(TAG,"Tarefas encontradas: " + tarefas.size());
                Log.d(TAG, Arrays.toString(tarefas.toArray()));

                RestServiceClient.this.setChanged();
                RestServiceClient.this.notifyObservers(tarefas);
                RestServiceClient.this.deleteObservers();
            }
        };

        restCallTask.execute();
    }

    public void createTask(Observer observer, final Tarefa tarefa) {
        // TODO: criar elemento usando POST, deve ser enviado um obj json e será recebido outro json
        addObserver(observer);

        AsyncTask<Tarefa, Void, Tarefa> restCallTask =
                new AsyncTask<Tarefa, Void, Tarefa>() {
                    @Override
                    protected Tarefa doInBackground(Tarefa... params) {
                        final String url = serviceRootUrl ;

                        HttpHeaders requestHeaders = new HttpHeaders();
                        requestHeaders.setAuthorization(getHttpAuthentication());
                        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                        RestTemplate restTemplate = new RestTemplate();
                        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                        try {
                            // Make the network request
                            Log.d(TAG, url);
                            HttpEntity<Tarefa> httpEntity =
                                    new HttpEntity<Tarefa>(tarefa, requestHeaders);
                            ResponseEntity<Tarefa> response;
                            response = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
                                    Tarefa.class);
                            return response.getBody();
                        } catch (HttpClientErrorException e) {
                            Log.e(TAG, e.getLocalizedMessage(), e);
                            return null;
                        } catch (ResourceAccessException e) {
                            Log.e(TAG, e.getLocalizedMessage(), e);
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(Tarefa tarefa) {
                        RestServiceClient.this.setChanged();
                        RestServiceClient.this.notifyObservers(tarefa);
                        RestServiceClient.this.deleteObservers();
                    }
                };

        restCallTask.execute();
    }

    public void updateTask(Observer observer, final Tarefa tarefa) {
        // TODO: atualizar elemento usando PUT, deve ser enviado um obj json e será recebido outro json
        addObserver(observer);

        AsyncTask<Tarefa, Void, Tarefa> restCallTask =
                new AsyncTask<Tarefa, Void, Tarefa>() {
                    @Override
                    protected Tarefa doInBackground(Tarefa... params) {
                        final String url = serviceRootUrl ;

                        HttpHeaders requestHeaders = new HttpHeaders();
                        requestHeaders.setAuthorization(getHttpAuthentication());
                        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                        RestTemplate restTemplate = new RestTemplate();
                        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                        try {
                            // Make the network request
                            Log.d(TAG, url);
                            HttpEntity<Tarefa> httpEntity =
                                    new HttpEntity<Tarefa>(tarefa, requestHeaders);
                            ResponseEntity<Tarefa> response;
                            response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity,
                                    Tarefa.class);
                            return response.getBody();
                        } catch (HttpClientErrorException e) {
                            Log.e(TAG, e.getLocalizedMessage(), e);
                            return null;
                        } catch (ResourceAccessException e) {
                            Log.e(TAG, e.getLocalizedMessage(), e);
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(Tarefa tarefa) {
                        RestServiceClient.this.setChanged();
                        RestServiceClient.this.notifyObservers(tarefa);
                        RestServiceClient.this.deleteObservers();
                    }
                };

        restCallTask.execute();
    }

    public void deleteTask(Observer observer, final Tarefa tarefa) {
        addObserver(observer);

        AsyncTask<Void, Void, Boolean> restCallTask =
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        HttpHeaders requestHeaders = new HttpHeaders();
                        requestHeaders.setAuthorization(getHttpAuthentication());
                        String url = String.format("%s/%s", serviceRootUrl, tarefa.getId().toString());
                        RestTemplate restTemplate = new RestTemplate();
                        try {
                            // Make the network request
                            Log.d(TAG, url + " Method: " + HttpMethod.DELETE);
                            HttpEntity<Object> entity = new HttpEntity<Object>(requestHeaders);
                            ResponseEntity<Void> response;
                            response = restTemplate.exchange(url, HttpMethod.DELETE,
                                    entity, Void.class);

                            return response.getStatusCode() == HttpStatus.OK;
                        } catch (HttpClientErrorException e) {
                            Log.e(TAG, e.getLocalizedMessage(), e);
                            return false;
                        } catch (ResourceAccessException e) {
                            Log.e(TAG, e.getLocalizedMessage(), e);
                            return false;
                        }
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        Log.d(TAG, "Apagou?  " + result);

                        RestServiceClient.this.setChanged();
                        RestServiceClient.this.notifyObservers(result);
                        RestServiceClient.this.deleteObservers();
                    }
                };
        restCallTask.execute();
    }
}
