package rocha.andre.api.domain.user.UseCase;

import org.springframework.stereotype.Component;

@Component
public class ValidateEmailUseCase {
    //FAZ A BUSCA NO DB PELO CAMPO token_confirmation, vê se match com o recebido na requisição
    //, se sim, põe o usuário como validated true
}
