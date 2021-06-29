import { saveSessionData } from 'core/utils/auth';
import { makeLogin } from 'core/utils/request';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useHistory, useLocation } from 'react-router';
import './styles.scss';
type FormState = {
    username: string;
    password: string;
}
type LocationState = {
    from: string;
}
function LoginCard() {
    const { register, handleSubmit, errors } = useForm<FormState>();
    const [hasError, setHasError] = useState(false);
    const history = useHistory();
    const location = useLocation<LocationState>();
    let { from } = location.state || { from: { pathname: "/home" } };

    const onSubmit = (data: FormState) => {
        makeLogin(data)
            .then(response => {
                setHasError(false);
                saveSessionData(response.data);
                history.replace(from);
            })
            .catch(() => {
                setHasError(true);
            })

            ;
    }
    return (
        <form className="login-card-main" onSubmit={handleSubmit(onSubmit)}>
            <h1 className="login-card-title">
                Informe suas credenciais:
           </h1>
           {hasError && (
                    <div className="alert alert-danger">
                        Usuário ou senha inválido!
                    </div>
            )}
            <input
                className="login-card-input"
                type="email"
                placeholder="Email"
                name="username"
                ref={register({
                    required: "Campo obrigatório",
                    pattern: {
                        value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                        message: "Email inválido"
                    }
                })}
            />
            {errors.username && (
                <div className="invalid-feedback d-block">
                    {errors.username.message}
                </div>
            )}
            <input
                className="login-card-input login-card-input-pwd"
                type="password"
                placeholder="Senha"
                name="password"
                ref={register({ required: "Campo obrigatório" })}
            />
            {errors.password && (
                <div className="invalid-feedback d-block">
                    {errors.password.message}
                </div>
            )}
            <button
                className="login-card-btn"
            >
                LOGAR
            </button>
        </form>
    );
}

export default LoginCard;
