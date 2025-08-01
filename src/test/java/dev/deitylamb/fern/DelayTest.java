package dev.deitylamb.fern;

import org.junit.jupiter.api.Test;

import dev.deitylamb.fern.transitions.Transitionable;

class DelayTest {

  @Test
  void delayShouldWork() {
    Transitionable<?> transition = Fern.transition(10).delay(5);
    transition.play();

    TestUtils.iter(transition, 5).shouldBe(0);

    TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

    assert transition.isPaused();
  }

  @Test
  void delayShouldWorkBeforeCircular() {
    Transitionable<?> transition = Fern.transition(10).delay(5).circular();
    transition.play();

    TestUtils.iter(transition, 5).shouldBe(0);

    TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

    TestUtils.iter(transition, 5).shouldBe(1);

    TestUtils.iter(transition, 10).shouldBe(t -> 1d - t / 10d);

    assert transition.isPaused();
  }

  @Test
  void delayShouldWorkAfterCircular() {
    Transitionable<?> transition = Fern.transition(10).circular().delay(5);
    transition.play();

    TestUtils.iter(transition, 5).shouldBe(0);

    TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

    TestUtils.iter(transition, 10).shouldBe(t -> 1d - t / 10d);

    assert transition.isPaused();
  }

  @Test
  void delayShouldWorkBeforeCircularAndLoop() {
    Transitionable<?> transition = Fern.transition(10).delay(5).circular().loop();
    transition.play();

    for (int i = 0; i < 10; i++) {
      TestUtils.iter(transition, 5).shouldBe(0);

      TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

      TestUtils.iter(transition, 5).shouldBe(1);

      TestUtils.iter(transition, 10).shouldBe(t -> 1d - t / 10d);
    }

    assert transition.isRunning();
  }

  @Test
  void delayShouldWorkAfterCircularAndLoop() {
    Transitionable<?> transition = Fern.transition(10).circular().loop().delay(5);
    transition.play();

    TestUtils.iter(transition, 5).shouldBe(0);

    for (int i = 0; i < 10; i++) {

      TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

      TestUtils.iter(transition, 10).shouldBe(t -> 1d - t / 10d);
    }

    assert transition.isRunning();
  }

  @Test
  void delayShouldWorkAfterCircularAndBeforeLoop() {
    Transitionable<?> transition = Fern.transition(10).circular().delay(5).loop();
    transition.play();

    for (int i = 0; i < 10; i++) {
      TestUtils.iter(transition, 5).shouldBe(0);

      TestUtils.iter(transition, 10).shouldBe(t -> t / 10d);

      TestUtils.iter(transition, 10).shouldBe(t -> 1d - t / 10d);
    }

    assert transition.isRunning();
  }

  @Test
  void delayShouldWorkWithReset() {
    Transitionable<?> transition = Fern.transition(10).delay(5);

    transition.play();

    TestUtils.iter(transition, 5).shouldBe(0);

    TestUtils.iter(transition, 7).shouldBe(t -> t / 10d);

    transition.reset();

    TestUtils.iter(transition, 5).shouldBe(0);
  }

  @Test
  void delayShouldWorkWithPause() {
    Transitionable<?> transition = Fern.transition(10).delay(5);

    transition.play();

    TestUtils.iter(transition, 3).shouldBe(0);

    transition.pause();

    TestUtils.iter(transition, 30).shouldBe(0);

    transition.play();

    TestUtils.iter(transition, 2).shouldBe(0);

    TestUtils.iter(transition, 7).shouldBe(t -> t / 10d);
  }

}
