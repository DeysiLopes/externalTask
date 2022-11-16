package br.looplex.externalTask;


import ch.qos.logback.core.net.SocketConnector;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

@Component
@ExternalTaskSubscription(topicName = "calculadora")
public class CalculadoraHandler implements ExternalTaskHandler {

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String operacao = externalTask.getVariable("operacao");
        Long primeiroArg = externalTask.getVariable("primeiroArg");
        Long segundoArg = externalTask.getVariable("segundoArg");
        Long resultado = 0L;

        switch (operacao) {
            case "soma":
                resultado = primeiroArg + segundoArg;
                break;
            case "subtracao":
                resultado = primeiroArg - segundoArg;
                break;
            case "multiplicacao":
                resultado = primeiroArg * segundoArg;
                break;
            case "divisao":
                resultado = primeiroArg / segundoArg;
                break;
        }

        VariableMap variaveis = Variables.createVariables();
        variaveis.put("resultado", resultado);
        externalTaskService.complete(externalTask, variaveis);

    }
}
