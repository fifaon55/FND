import React, { useEffect } from 'react';
import { Button, Col, Alert, Row } from 'reactstrap';
import { connect } from 'react-redux';

import { AvForm, AvField } from 'availity-reactstrap-validation';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { saveAccountSettings, reset } from './settings.reducer';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export interface IUserSettingsProps extends StateProps, DispatchProps {}

export const SettingsPage = (props: IUserSettingsProps) => {
  useEffect(() => {
    props.getSession();
    return () => {
      props.reset();
    };
  }, []);

  const handleValidSubmit = (event, values) => {
    const account = {
      ...props.account,
      ...values,
    };

    props.saveAccountSettings(account);
    event.persist();
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="settings-title">User settings for {props.account.login}</h2>
          <AvForm id="settings-form" onValidSubmit={handleValidSubmit}>
            {/* Email */}
            <AvField
              name="email"
              label="Email"
              placeholder={'Your email'}
              type="email"
              disabled
              validate={{
                required: { value: true, errorMessage: 'Your email is required.' },
                minLength: { value: 5, errorMessage: 'Your email is required to be at least 5 characters.' },
                maxLength: { value: 254, errorMessage: 'Your email cannot be longer than 50 characters.' },
              }}
              value={props.account.email}
            />
            {/* First name */}
            {/*<AvField*/}
            {/*  className="form-control"*/}
            {/*  name="firstName"*/}
            {/*  label="First Name"*/}
            {/*  id="firstName"*/}
            {/*  placeholder="Your first name"*/}
            {/*  validate={{*/}
            {/*    required: { value: true, errorMessage: 'Your first name is required.' },*/}
            {/*    minLength: { value: 1, errorMessage: 'Your first name is required to be at least 1 character' },*/}
            {/*    maxLength: { value: 50, errorMessage: 'Your first name cannot be longer than 50 characters' },*/}
            {/*  }}*/}
            {/*  value={props.account.firstName}*/}
            {/*/>*/}
            {/* Last name */}
            <AvField
              className="form-control"
              name="lastName"
              label="Phone Number"
              id="lastName"
              placeholder="Your phone number"
              validate={{
                required: { value: true, errorMessage: 'Your phone number is required.' },
                minLength: { value: 1, errorMessage: 'Your phone number is required to be at least 1 character' },
                maxLength: { value: 15, errorMessage: 'Your phone number cannot be longer than 15 characters' },
              }}
              value={props.account.lastName}
            />
            <Button tag={Link} to="/" replace color="info">
              <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
            </Button>
            &nbsp;
            <Button color="primary" type="submit">
              Save
            </Button>
          </AvForm>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ authentication }: IRootState) => ({
  account: authentication.account,
  isAuthenticated: authentication.isAuthenticated,
});

const mapDispatchToProps = { getSession, saveAccountSettings, reset };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SettingsPage);
