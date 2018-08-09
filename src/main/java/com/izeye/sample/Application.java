package com.izeye.sample;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.dropwizard.DropwizardConfig;
import io.micrometer.core.instrument.dropwizard.DropwizardMeterRegistry;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

/**
 * Sample application.
 *
 * @author Johnny Lim
 */
@SpringBootApplication
public class Application {

	public static MeterRegistry consoleLoggingRegistry() {
		MetricRegistry dropwizardRegistry = new MetricRegistry();

		ConsoleReporter reporter = ConsoleReporter.forRegistry(dropwizardRegistry)
				.convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.build();
		reporter.start(10, TimeUnit.SECONDS);

		DropwizardConfig consoleConfig = new DropwizardConfig() {
			@Override
			public String prefix() {
				return "console";
			}

			@Override
			public String get(String key) {
				System.out.println("SpringReporter::consoleLoggingRegistry getKey: " + key);
				return null;
			}
		};

		return new DropwizardMeterRegistry(consoleConfig, dropwizardRegistry, HierarchicalNameMapper.DEFAULT, Clock.SYSTEM) {
			@Override
			protected Double nullGaugeValue() {
				return null;
			}
		};
	}

	public static void main(String[] args) {
		Metrics.addRegistry(consoleLoggingRegistry());
		SpringApplication.run(Application.class, args);
	}

}
