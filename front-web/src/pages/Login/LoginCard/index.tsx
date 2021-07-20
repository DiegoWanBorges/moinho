import { saveSessionData } from 'core/utils/auth';
import { makeLogin } from 'core/utils/request';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useHistory, useLocation } from 'react-router';
import './styles.scss';
import Loader from "react-loader-spinner";
import EyesOpened from 'core/assets/images/eyesOpened.png'
import EyesClosed from 'core/assets/images/eyesClosed.png'
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
    const [isLoading, setIsLoading] = useState(false);
    const location = useLocation<LocationState>();
    let { from } = location.state || { from: { pathname: "/home" } };
    const [hidePassword, setHidePassword] = useState(false);
    const onSubmit = (data: FormState) => {
        setIsLoading(true)
        makeLogin(data)
            .then(response => {
                setHasError(false);
                saveSessionData(response.data);
                history.replace(from);
            })
            .catch(() => {
                setHasError(true);
            })
            .finally(() => {
                setIsLoading(false)
            })

            ;
    }
    const hidenShowPwd = (e : React.MouseEvent<HTMLButtonElement>) =>{
        e.preventDefault();
        setHidePassword(!hidePassword)
    }
    return (
        <form className="login-card-main" onSubmit={handleSubmit(onSubmit)}>

            {
                isLoading ?
                    <div className="login-card-loader">
                        <Loader
                            type="Rings"
                            height={100}
                            width={100}
                            color="#0670B8"
                        />
                    </div>
                    :
                    (
                        <>
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
                            <div className="login-card-pwd">
                                <input
                                    className="login-card-input login-card-input-pwd"
                                    type={hidePassword ? "text" : "password"}
                                    placeholder="Senha"
                                    name="password"
                                    ref={register({ required: "Campo obrigatório" })}
                                />
                                <button
                                    className="card-login-hide-password"
                                    onClick={(e) => hidenShowPwd(e)}
                                >
                                    <img src={hidePassword ? EyesClosed : EyesOpened} alt="" />
                                </button>
                            </div>

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
                        </>
                    )
            }

        </form>
    );
}

export default LoginCard;
