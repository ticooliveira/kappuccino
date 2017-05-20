package br.com.concretesolutions.kappuccino.custom.recyclerView

import android.support.annotation.IdRes
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.v7.widget.RecyclerView
import br.com.concretesolutions.kappuccino.extensions.clickChildView
import br.com.concretesolutions.kappuccino.extensions.clickItem
import br.com.concretesolutions.kappuccino.counters.CountAssertion
import br.com.concretesolutions.kappuccino.extensions.longClickItem
import br.com.concretesolutions.kappuccino.matchers.RecyclerViewMatcher
import org.hamcrest.Matchers.not

class RecyclerViewMethods(private val recyclerViewId: Int) {

    fun sizeIs(size: Int): RecyclerViewMethods {
        onView(withId((recyclerViewId))).check(CountAssertion(size))
        return this
    }

    fun atPosition(vararg positions: Int, func: Interactions.() -> Unit): RecyclerViewMethods {
        for(position in positions)
            Interactions(recyclerViewId, position).apply { func() }
        return this
    }

    class Interactions(val recyclerViewId: Int, val position: Int) {

        fun click(): Interactions {
            onView(withId((recyclerViewId))).clickItem(position)
            return this
        }

        fun longClick(): Interactions {
            onView(withId((recyclerViewId))).longClickItem(position)
            return this
        }

        fun clickChildView(@IdRes viewId: Int): Interactions {
            onView(withId((recyclerViewId))).clickChildView(position, viewId)
            return this
        }

        fun displayed(@IdRes viewId: Int): Interactions {
            onView(RecyclerViewMatcher(recyclerViewId)
                    .atPositionOnView(position, viewId)).check(matches(isDisplayed()))
            return this
        }

        fun notDisplayed(@IdRes viewId: Int): Interactions {
            onView(RecyclerViewMatcher(recyclerViewId)
                    .atPositionOnView(position, viewId)).check(matches(not(isDisplayed())))
            return this
        }

        fun scroll(): Interactions {
            onView(withId((recyclerViewId))).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
            return this
        }
    }


}
