package kr.co.shortenurlservice.application;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

import kr.co.shortenurlservice.domain.NotFoundShortenUrlException;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateRequestDto;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SimpleShortenUrlServiceTest {

  @Autowired private SimpleShortenUrlService simpleShortenUrlService;

  @Test
  @DisplayName("URL을 단축한 후 단축된 URL 키로 조회하면 원래 URL이 조회되어야 한다.")
  void shortenUrlAddTest() {
    String expectedOriginalUrl = "https://www.hanbit.co.kr/";
    ShortenUrlCreateRequestDto shortenUrlCreateRequestDto =
        new ShortenUrlCreateRequestDto(expectedOriginalUrl);

    ShortenUrlCreateResponseDto shortenUrlCreateResponseDto =
        simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
    String shortenUrlKey = shortenUrlCreateResponseDto.getShortenUrlKey();

    String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);

    assertTrue(originalUrl.equals(expectedOriginalUrl));
  }

  @Test
  @DisplayName("존재하지 않는 단축 URL 키로 조회하는 경우 NotFoundShortenUrlException 예외가 발생한다.")
  void shouldThrowExceptionWhenURLKeyDoesNotExist() {
    assertThatExceptionOfType(NotFoundShortenUrlException.class)
        .isThrownBy(() -> simpleShortenUrlService.getOriginalUrlByShortenUrlKey("does-not-exist"));
  }
}
