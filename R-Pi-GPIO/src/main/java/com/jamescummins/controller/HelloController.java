package com.jamescummins.controller;

import javax.annotation.PreDestroy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@RestController
public class HelloController {
	// create gpio controller
    private GpioController gpio ;
    
    // provision gpio pin #01 as an output pin and turn on
    private GpioPinDigitalOutput pin ;

    
    public HelloController(){
    	this.gpio = GpioFactory.getInstance();
    	this.pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
    }
    
    
    @RequestMapping("/hello")
    public String hello(@RequestParam(value="n", required=false) String name) {
        name = name!=null?name:"";
    	return "Greetings " + name + "!";
    }
    
	@RequestMapping("/welcome")
	public String home() {
	    return "Hello World!";
	}
    
	@RequestMapping("/gpio")
	public String gpio() throws InterruptedException{
		
		
/*        // set shutdown state for this pin
        pin.setShutdownOptions(true, PinState.LOW);

        System.out.println("--> GPIO state should be: ON");

        Thread.sleep(5000);
        
        // turn off gpio pin #01
        pin.low();
        System.out.println("--> GPIO state should be: OFF");

        Thread.sleep(5000);
*/
        // toggle the current state of gpio pin #01 (should turn on)
        this.pin.toggle();
/*        System.out.println("--> GPIO state should be: ON");

        Thread.sleep(5000);

        // toggle the current state of gpio pin #01  (should turn off)
        pin.toggle();
        System.out.println("--> GPIO state should be: OFF");
        
        Thread.sleep(5000);

        // turn on gpio pin #01 for 1 second and then off
        System.out.println("--> GPIO state should be: ON for only 1 second");
        pin.pulse(1000, true); // set second argument to 'true' use a blocking call
        
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpio.shutdown();
*/	
        
	    return pin.getState().isHigh()?"ON":"OFF";
	}

	@PreDestroy
	private void gpioShutdown(){
		pin.low();
	}

}