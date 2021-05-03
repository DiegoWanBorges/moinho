import './styles.scss';

function LoginCard() {
    return (
        <div className="login-card-main">
            <h1 className="login-card-title">
                Informe suas credênciais:
           </h1>

            <input
                className="login-card-input"
                type="text"
                placeholder="Email"
                name="username"
            />

            <input
                className="login-card-input login-card-input-pwd"
                type="password"
                placeholder="Senha"
                name="password"
            />
            <button
                className="login-card-btn"
            >
                LOGAR
            </button>

        </div>
    );
}

export default LoginCard;
