package com.apap.tutorial7.controller;

import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.rest.DealerDetail;
import com.apap.tutorial7.rest.Setting;
import com.apap.tutorial7.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/dealer")
public class DealerController {
    @Autowired
    private DealerService dealerService;

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate rest(){
        return new RestTemplate();
    }

    @GetMapping(value = "/status/{dealerId}")
    private String getStatus(@PathVariable ("dealerId") long dealerId) throws Exception{
        String path = Setting.dealerUrl + "/dealer?id=" + dealerId;
        return restTemplate.getForEntity(path, String.class).getBody();
    }

    @GetMapping(value = "/full/{dealerId}")
    private DealerDetail postStatus(@PathVariable ("dealerId") long dealerId) throws Exception {
        String path = Setting.dealerUrl + "/dealer";
        DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
        DealerDetail detail = restTemplate.postForObject(path, dealer, DealerDetail.class);
        return detail;
    }

    @PostMapping(value = "/add")
    private DealerModel addDealerSubmit(@RequestBody DealerModel dealer) {
        return dealerService.addDealer(dealer);
    }

    @GetMapping(value = "/{dealerId}")
    private DealerModel viewDealer(@PathVariable("dealerId") long dealerId, Model model){
        return dealerService.getDealerDetailById(dealerId).get();
    }

    @DeleteMapping(value= "/delete")
    private String deleteDealer(@RequestParam("dealerId") long id, Model model){
        DealerModel dealer = dealerService.getDealerDetailById(id).get();
        dealerService.deleteDealer(dealer);
        return "delete-success";
    }

    @PutMapping(value="/{id}")
    private String updateDealerSubmit(
            @PathVariable(value="id") long id,
            @RequestParam("alamat") String alamat,
            @RequestParam("noTelp") String noTelp){
        DealerModel dealer = dealerService.getDealerDetailById(id).get();
        if (dealer.equals(null)){
            return "no-dealer";
        }
        dealer.setAlamat(alamat);
        dealer.setNoTelp(noTelp);
        dealerService.addDealer(dealer);
        return "update-success";
    }

    @GetMapping()
    private List<DealerModel> viewAllDealer(Model model){
        return dealerService.getAllDealer();
    }

}