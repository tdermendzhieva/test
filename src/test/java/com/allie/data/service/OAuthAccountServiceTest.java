package com.allie.data.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.allie.data.dto.OAuthAccountDTO;
import com.allie.data.factory.OAuthAccountFactory;
import com.allie.data.jpa.model.OAuthAccount;
import com.allie.data.repository.OAuthAccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OAuthAccountServiceTest {

    private OAuthAccount newModel;
    private OAuthAccountDTO newValidAccount;
    private OAuthAccountService service;
    private OAuthAccountDTO newMissingIdAccount;

    @Mock
    private OAuthAccountFactory oAuthAccountFactory;

    @Mock
    private OAuthAccountRepository oAuthAccountRepository;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLogEvent;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);
        logger.setLevel(Level.ALL);

        newModel = new OAuthAccount();

        newMissingIdAccount = new OAuthAccountDTO();

        newValidAccount = new OAuthAccountDTO();
        newValidAccount.setAllieId("1");

        when(oAuthAccountFactory.createOAuthAccount(newValidAccount)).thenReturn(newModel);

        service = new OAuthAccountService(oAuthAccountRepository, oAuthAccountFactory);
    }

    @Test
    public void testServiceInsertsOAuthAccountIntoRepository() throws Exception {

        service.insert(newValidAccount);

        verify(oAuthAccountRepository).insert(newModel);

        verify(mockAppender).doAppend(captorLogEvent.capture());
        LoggingEvent loggingEvent = captorLogEvent.getValue();
        Assert.assertThat(loggingEvent.getLevel(), is(Level.DEBUG));
    }

    @Test
    public void testThrownExceptionAndLogMessageWhenAllieIdIsNullOnInsert() throws Exception {

        try {
            service.insert(newMissingIdAccount);
        } catch (Exception e) {

            verify(mockAppender).doAppend(captorLogEvent.capture());
            LoggingEvent loggingEvent = captorLogEvent.getValue();
            Assert.assertThat(loggingEvent.getLevel(), is(Level.ERROR));
            Assert.assertThat(loggingEvent.getFormattedMessage(), is("Account missing required field, received: OAuthAccountDTO { allieId: 'null', accountIssuer: 'null' }"));

            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            assertThat(e.getMessage(), is("Account must have an allieId"));
            return;
        }
        assertThat("should not get this far", true, equalTo(false));
    }

    @Test
    public void testShouldReturnOAuthAccountByAllieId() throws Exception {
        String allieId = "1";
        List<OAuthAccount> oAuthAccounts = new ArrayList<>();
        oAuthAccounts.add(new OAuthAccount());
        List<OAuthAccountDTO> oAuthAccountDTOs = new ArrayList<>();
        oAuthAccountDTOs.add(new OAuthAccountDTO());

        when(oAuthAccountRepository.findByAllieId(allieId)).thenReturn(oAuthAccounts);
        when(oAuthAccountFactory.createOAuthAccountDTO(oAuthAccounts.get(0))).thenReturn(oAuthAccountDTOs.get(0));

        List<OAuthAccountDTO> response = service.getByAllieId(allieId);

        assertThat(response, is(oAuthAccountDTOs));
    }

    @Test
    public void testThrownExceptionAndLogMessageWhenAllieIdIsNullOnFind() throws Exception {

        try {
            service.getByAllieId(null);
        } catch (Exception e) {

            verify(mockAppender).doAppend(captorLogEvent.capture());
            LoggingEvent loggingEvent = captorLogEvent.getValue();
            Assert.assertThat(loggingEvent.getLevel(), is(Level.ERROR));
            Assert.assertThat(loggingEvent.getFormattedMessage(), is("OAuthAccountService getByAllieId must not be null or empty"));

            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            assertThat(e.getMessage(), is("AllieId must not be null or empty"));
            return;
        }
        assertThat("should not get this far", true, equalTo(false));
    }
}
