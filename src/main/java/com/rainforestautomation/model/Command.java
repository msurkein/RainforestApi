package com.rainforestautomation.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Command")
public class Command {
    private String name;

    public Command(String name) {
        this.name = name;
    }

    protected Command() {
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public enum Commands {
        initialize,
        restart,
        factory_reset,
        get_connection_status,
        get_device_info,
        get_schedule,
        set_schedule,
        set_schedule_default,
        get_meter_list,
        get_meter_info,
        get_network_info,
        set_meter_info,
        get_time,
        get_message,
        confirm_message,
        get_current_price,
        set_current_price,
        get_instantaneous_demand,
        get_current_period_usage,
        get_last_period_uasge,
        close_current_period,
        set_fast_poll,
        get_profile_data;
        private Command command;
        Commands() {
            command = new Command(this.name());
        }

        public Command getCommand() {
            return command;
        }
    }
}
