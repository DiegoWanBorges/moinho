import { getSessionData, LoginResponse } from 'core/utils/auth';
import { useEffect } from 'react';
import { useState } from 'react';
import ImageLogin from '../../core/assets/images/login.png'
import './styles.scss';

function Home() {
    const [user, setUser] = useState<LoginResponse>();

    useEffect(() => {
        setUser(getSessionData);
    }, [])
    return (
        <div className="home-main">
            <div className="home-name">
                <h6>{`Bem Vindo, ${user?.name}!`}</h6>
            </div>
            <div className="home-img"> 
                <img className="home-img-moinho" alt="" src={ImageLogin} />
            </div>
        </div>
    );
}
export default Home;
