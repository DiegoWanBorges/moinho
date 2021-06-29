import './styles.scss';
import ImageLogin from '../../core/assets/images/login.png'
import LoginCard from './LoginCard';
function Login() {
  return (
    <div className="login-main">
      <div className="login-inf">
        <img className="login-img" alt="" src={ImageLogin} />
        <h3 className="login-inf-title">Controle de produção<br /> e apuração de custo</h3>
      </div>
      <LoginCard />
    </div>
  );
}

export default Login;
